/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - Initial implementation
 *       truzzt GmbH - PostgreSQL implementation
 *
 */

package com.truzzt.extension.logginghouse.client.store.sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truzzt.extension.logginghouse.client.spi.store.LoggingHouseMessageStore;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessage;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessageStatus;
import com.truzzt.extension.logginghouse.client.store.sql.schema.LoggingHouseEventStatements;
import org.eclipse.edc.spi.persistence.EdcPersistenceException;
import org.eclipse.edc.sql.QueryExecutor;
import org.eclipse.edc.sql.store.AbstractSqlStore;
import org.eclipse.edc.transaction.datasource.spi.DataSourceRegistry;
import org.eclipse.edc.transaction.spi.TransactionContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SqlLoggingHouseMessageStore extends AbstractSqlStore implements LoggingHouseMessageStore {

    private final LoggingHouseEventStatements statements;

    public SqlLoggingHouseMessageStore(DataSourceRegistry dataSourceRegistry,
                                       String dataSourceName,
                                       TransactionContext transactionContext,
                                       ObjectMapper objectMapper,
                                       LoggingHouseEventStatements statements,
                                       QueryExecutor queryExecutor) {
        super(dataSourceRegistry, dataSourceName, transactionContext, objectMapper, queryExecutor);
        this.statements = statements;
    }

    @Override
    public void save(LoggingHouseMessage event) {

        Objects.requireNonNull(event);
        Objects.requireNonNull(event.getEventType());
        Objects.requireNonNull(event.getEventId());
        Objects.requireNonNull(event.getEventToLog());
        Objects.requireNonNull(event.getProcessId());
        Objects.requireNonNull(event.getStatus());
        Objects.requireNonNull(event.getCreatedAt());

        transactionContext.execute(() -> {
            try (var connection = getConnection()) {
                queryExecutor.execute(connection, statements.getInsertTemplate(),
                        event.getEventType(),
                        event.getEventId(),
                        toJson(event.getEventToLog()),
                        event.getCreateProcess(),
                        event.getProcessId(),
                        event.getConsumerId(),
                        event.getProviderId(),
                        event.getStatus().getCode(),
                        mapFromZonedDateTime(event.getCreatedAt())
                );
            } catch (Exception e) {
                throw new EdcPersistenceException("Error executing INSERT statement", e);
            }
        });
    }

    @Override
    public List<LoggingHouseMessage> listPending() {
        return transactionContext.execute(() -> {
            try {
                return queryExecutor.query(getConnection(), true, this::mapResultSet,
                                statements.getSelectPendingStatement(),
                                LoggingHouseMessageStatus.PENDING.getCode())
                        .collect(Collectors.toList());
            } catch (SQLException e) {
                throw new EdcPersistenceException("Error executing SELECT statement", e);
            }
        });
    }

    @Override
    public void updateSent(long id, String receipt) {
        transactionContext.execute(() -> {
            try {
                queryExecutor.execute(getConnection(),
                        statements.getUpdateSentTemplate(),
                        LoggingHouseMessageStatus.SENT.getCode(),
                        receipt,
                        mapFromZonedDateTime(ZonedDateTime.now()),
                        id);
            } catch (SQLException e) {
                throw new EdcPersistenceException("Error executing UPDATE statement", e);
            }
        });
    }

    @Override
    public void updateRetry(long id) {
        transactionContext.execute(() -> {
            try {
                queryExecutor.execute(getConnection(),
                        statements.getUpdateRetryTemplate(),
                        id);
            } catch (SQLException e) {
                throw new EdcPersistenceException("Error executing UPDATE statement", e);
            }
        });
    }

    @Override
    public void updateFailed(long id) {
        transactionContext.execute(() -> {
            try {
                queryExecutor.execute(getConnection(),
                        statements.getUpdateFailedTemplate(),
                        LoggingHouseMessageStatus.FAILED.getCode(),
                        id);
            } catch (SQLException e) {
                throw new EdcPersistenceException("Error executing UPDATE statement", e);
            }
        });
    }

    private Long mapFromZonedDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.toEpochSecond() : null;
    }

    private ZonedDateTime mapToZonedDateTime(ResultSet resultSet, String column) throws Exception {
        return resultSet.getString(column) != null ?
                Instant.ofEpochSecond(resultSet.getLong(column)).atZone(ZoneId.of("Z")) :
                null;
    }

    private LoggingHouseMessage mapResultSet(ResultSet resultSet) throws Exception {
        var status = LoggingHouseMessageStatus.codeOf(resultSet.getString(statements.getStatusColumn()));

        return LoggingHouseMessage.Builder.newInstance()
                .id(resultSet.getLong(statements.getIdColumn()))
                .eventType(resultSet.getString(statements.getEventTypeColumn()))
                .eventId(resultSet.getString(statements.getEventIdColumn()))
                .eventToLog(resultSet.getString(statements.getEventToLogColumn()))
                .createProcess(resultSet.getBoolean(statements.getCreateProcessColumn()))
                .processId(resultSet.getString(statements.getProcessIdColumn()))
                .consumerId(resultSet.getString(statements.getConsumerIdColumn()))
                .providerId(resultSet.getString(statements.getProviderIdColumn()))
                .status(status)
                .createdAt(mapToZonedDateTime(resultSet, statements.getCreatedAtColumn()))
                .retries(resultSet.getInt(statements.getRetriesColumn()))
                .build();
    }
}
