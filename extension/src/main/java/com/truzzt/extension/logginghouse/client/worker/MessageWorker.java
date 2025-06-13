/*
 *  Copyright (c) 2022 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

package com.truzzt.extension.logginghouse.client.worker;

import com.truzzt.extension.logginghouse.client.events.messages.CreateProcessMessage;
import com.truzzt.extension.logginghouse.client.events.messages.LogMessage;
import com.truzzt.extension.logginghouse.client.events.messages.LogMessageReceipt;
import com.truzzt.extension.logginghouse.client.spi.store.LoggingHouseMessageStore;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessage;
import org.eclipse.edc.spi.EdcException;
import org.eclipse.edc.spi.message.RemoteMessageDispatcherRegistry;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.response.StatusResult;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

public class MessageWorker {
    private final String participantId;
    private final Monitor monitor;
    private final RemoteMessageDispatcherRegistry dispatcherRegistry;
    private final URI connectorBaseUrl;
    private final URL loggingHouseUrl;
    private final LoggingHouseMessageStore store;
    private final int retryLimit;
    private final String workerId;

    public MessageWorker(String participantId,
                         Monitor monitor,
                         RemoteMessageDispatcherRegistry dispatcherRegistry,
                         URI connectorBaseUrl,
                         URL loggingHouseUrl,
                         LoggingHouseMessageStore store,
                         int retryLimit) {
        this.participantId = participantId;
        this.monitor = monitor;
        this.dispatcherRegistry = dispatcherRegistry;
        this.connectorBaseUrl = connectorBaseUrl;
        this.loggingHouseUrl = loggingHouseUrl;
        this.store = store;
        this.retryLimit = retryLimit;

        workerId = "Worker-" + UUID.randomUUID();
    }

    public String getId() {
        return workerId;
    }

    public CompletableFuture<Boolean> run(LoggingHouseMessage message) {
        try {
            monitor.debug("Worker " + workerId + " processing message with event of type " + message.getEventType() + " and id " + message.getEventId());
            process(message);

            return CompletableFuture.completedFuture(true);

        } catch (Exception e) {
            monitor.severe(e.getMessage());
            return CompletableFuture.failedFuture(new EdcException(e));
        }
    }

    public void process(LoggingHouseMessage message) {
        try {
            var pid = message.getProcessId();

            // Create Process
            if (message.getCreateProcess()) {
                var extendedProcessUrl = new URL(loggingHouseUrl + "/process/" + pid);
                try {
                    createProcess(message, extendedProcessUrl).join();
                } catch (Exception e) {
                    monitor.warning("CreateProcess returned error (ignore it when the process already exists): " + e.getMessage());
                }
            }

            // Log Message
            var extendedLogUrl = new URL(loggingHouseUrl + "/messages/log/" + pid);
            try {
                if (message.getRetries() >= retryLimit) {
                    monitor.info("Message with id " + message.getEventId() + " reached retry limit " + retryLimit + ", will be marked as failed");
                    store.updateFailed(message.getId());
                    return;
                }

                var response = logMessage(message, extendedLogUrl).join();
                response.onSuccess(success -> {
                    monitor.info("Received receipt from LoggingHouse for message with id " + message.getEventId());
                    store.updateSent(message.getId(), success.data());

                }).onFailure(failure -> {
                    monitor.info(format("Received error (%s) from LoggingHouse for message with id %s", failure.getFailureDetail(), message.getEventId()));
                    retryMessage(message);
                });

            } catch (Exception e) {
                monitor.severe("Could not log message to LoggingHouse", e);
                retryMessage(message);
            }
        } catch (MalformedURLException e) {
            throw new EdcException("Could not create extended clearinghouse URL");
        }
    }

    public CompletableFuture<StatusResult<Object>> createProcess(LoggingHouseMessage message, URL loggingHouseUrl) {

        List<String> processOwners = new ArrayList<>();
        processOwners.add(message.getConsumerId());
        processOwners.add(message.getProviderId());

        monitor.info("Creating process in LoggingHouse with id: " + message.getProcessId());
        var logMessage = new CreateProcessMessage(participantId, loggingHouseUrl, connectorBaseUrl, message.getProcessId(), processOwners);

        return dispatcherRegistry.dispatch(Object.class, logMessage);
    }

    public CompletableFuture<StatusResult<LogMessageReceipt>> logMessage(LoggingHouseMessage message, URL clearingHouseLogUrl) {

        monitor.info("Logging message to LoggingHouse with type " + message.getEventType() + " and id " + message.getEventId());
        var logMessage = new LogMessage(participantId, clearingHouseLogUrl, connectorBaseUrl, message.getEventToLog());

        return dispatcherRegistry.dispatch(LogMessageReceipt.class, logMessage);
    }

    private void retryMessage(LoggingHouseMessage message) {
        var nextRetry = message.getRetries() + 1;

        if (nextRetry < retryLimit) {
            var remainingRetries = retryLimit - nextRetry;
            monitor.info("Message with id " + message.getEventId() + " will be retried " + remainingRetries + " times");
            store.updateRetry(message.getId());
        } else {
            monitor.info("Message with id " + message.getEventId() + " reached retry limit " + retryLimit + ", will be marked as failed");
            store.updateFailed(message.getId());
        }
    }

}
