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

package com.truzzt.extension.logginghouse.client.store.sql.schema;

import static java.lang.String.format;

public class BaseSqlDialectStatements implements LoggingHouseEventStatements {

    @Override
    public String getInsertTemplate() {
        return format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?%s, ?, ?, ?, ?, ?, ?)",
                getLoggingHouseMessageTable(),
                getEventTypeColumn(),
                getEventIdColumn(),
                getEventToLogColumn(),
                getCreateProcessColumn(),
                getProcessIdColumn(),
                getConsumerIdColumn(),
                getProviderIdColumn(),
                getStatusColumn(),
                getCreatedAtColumn(),
                getFormatAsJsonOperator()
        );
    }

    @Override
    public String getSelectPendingStatement() {
        return format("SELECT * FROM %s WHERE %s = ? ORDER BY %s asc, %s asc",
                getLoggingHouseMessageTable(),
                getStatusColumn(),
                getCreatedAtColumn(),
                getIdColumn());
    }

    @Override
    public String getUpdateSentTemplate() {
        return format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                getLoggingHouseMessageTable(),
                getStatusColumn(),
                getReceiptColumn(),
                getSentAtColumn(),
                getIdColumn());
    }

    @Override
    public String getUpdateRetryTemplate() {
        return format("UPDATE %s SET %s = %s + 1 WHERE %s = ?",
                getLoggingHouseMessageTable(),
                getRetriesColumn(),
                getRetriesColumn(),
                getIdColumn());
    }

    @Override
    public String getUpdateFailedTemplate() {
        return format("UPDATE %s SET %s = %s + 1, %s = ? WHERE %s = ?",
                getLoggingHouseMessageTable(),
                getRetriesColumn(),
                getRetriesColumn(),
                getStatusColumn(),
                getIdColumn());
    }
}
