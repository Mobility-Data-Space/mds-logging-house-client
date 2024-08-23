/*
 *  Copyright (c) 2022 sovity GmbH
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       sovity GmbH - initial API and implementation
 *
 */

package com.truzzt.extension.logginghouse.client.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truzzt.extension.logginghouse.client.spi.store.LoggingHouseMessageStore;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessage;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessageStatus;
import org.eclipse.edc.connector.contract.spi.event.contractnegotiation.ContractNegotiationFinalized;
import org.eclipse.edc.connector.contract.spi.negotiation.store.ContractNegotiationStore;
import org.eclipse.edc.connector.contract.spi.types.agreement.ContractAgreement;
import org.eclipse.edc.connector.transfer.spi.event.TransferProcessEvent;
import org.eclipse.edc.connector.transfer.spi.store.TransferProcessStore;
import org.eclipse.edc.connector.transfer.spi.types.TransferProcess;
import org.eclipse.edc.spi.event.Event;
import org.eclipse.edc.spi.event.EventEnvelope;
import org.eclipse.edc.spi.event.EventSubscriber;
import org.eclipse.edc.spi.monitor.Monitor;

import java.time.ZonedDateTime;
import java.util.Objects;

public class LoggingHouseEventSubscriber implements EventSubscriber {

    private final LoggingHouseMessageStore loggingHouseMessageStore;
    private final ContractNegotiationStore contractNegotiationStore;
    private final TransferProcessStore transferProcessStore;
    private final Monitor monitor;
    private final ObjectMapper objectMapper;

    public LoggingHouseEventSubscriber(
            LoggingHouseMessageStore loggingHouseMessageStore,
            ContractNegotiationStore contractNegotiationStore,
            TransferProcessStore transferProcessStore,
            Monitor monitor,
            ObjectMapper objectMapper) {
        this.loggingHouseMessageStore = loggingHouseMessageStore;
        this.contractNegotiationStore = contractNegotiationStore;
        this.transferProcessStore = transferProcessStore;
        this.monitor = monitor;
        this.objectMapper = objectMapper;
    }

    @Override
    public <E extends Event> void on(EventEnvelope<E> event) {
        if (event.getPayload() instanceof ContractNegotiationFinalized contractNegotiationFinalized) {
            monitor.debug("Storing ContractNegotiationFinalized event with id " + event.getId());

            var contractAgreement = resolveContractAgreement(contractNegotiationFinalized);
            try {
                storeContractAgreement(contractAgreement);
            } catch (Exception e) {
                monitor.warning("Could not store ContractNegotiation: " + e.getMessage());
            }
        } else if (event.getPayload() instanceof TransferProcessEvent transferProcessEvent) {
            monitor.debug("Storing TransferProcess event with id " + event.getId());

            var transferProcess = resolveTransferProcess(transferProcessEvent);
            try {
                storeTransferProcess(transferProcess);
            } catch (Exception e) {
                monitor.warning("Could not store TransferProcess: " + e.getMessage());
            }
        } else if (event.getPayload() instanceof CustomLoggingHouseEvent customLoggingHouseEvent) {
            monitor.debug("Storing CustomLoggingHouseEvent for Logginghouse PID: " + customLoggingHouseEvent.getProcessId());

            try {
                storeCustomLoggingHouseEvent(customLoggingHouseEvent);
            } catch (Exception e) {
                monitor.warning("Could not store CustomLoggingHouseEvent: " + e.getMessage());
            }
        }
    }

    private ContractAgreement resolveContractAgreement(ContractNegotiationFinalized contractNegotiationFinalized) {
        var contractNegotiationId = contractNegotiationFinalized.getContractNegotiationId();
        var contractNegotiation = contractNegotiationStore.findById(contractNegotiationId);
        return Objects.requireNonNull(contractNegotiation).getContractAgreement();
    }

    private TransferProcess resolveTransferProcess(TransferProcessEvent transferProcessEvent) {
        var transferProcessId = transferProcessEvent.getTransferProcessId();
        return transferProcessStore.findById(transferProcessId);
    }

    public void storeContractAgreement(ContractAgreement contractAgreement) throws JsonProcessingException {
        monitor.info("Storing ContractAgreement to send to LoggingHouse");

        var eventPayload = objectMapper.writeValueAsString(contractAgreement);

        var message = LoggingHouseMessage.Builder.newInstance()
                .eventType(contractAgreement.getClass().getSimpleName())
                .eventId(contractAgreement.getId())
                .eventToLog(eventPayload)
                .createProcess(true)
                .processId(contractAgreement.getId())
                .consumerId(contractAgreement.getConsumerId())
                .providerId(contractAgreement.getProviderId())
                .status(LoggingHouseMessageStatus.PENDING)
                .createdAt(ZonedDateTime.now())
                .build();
        loggingHouseMessageStore.save(message);
    }

    public void storeTransferProcess(TransferProcess transferProcess) throws JsonProcessingException {
        monitor.info("Storing TransferProcess to send to LoggingHouse");

        var eventPayload = objectMapper.writeValueAsString(transferProcess);

        var message = LoggingHouseMessage.Builder.newInstance()
                .eventType(transferProcess.getClass().getSimpleName())
                .eventId(transferProcess.getId())
                .eventToLog(eventPayload)
                .createProcess(false)
                .processId(transferProcess.getContractId())
                .status(LoggingHouseMessageStatus.PENDING)
                .createdAt(ZonedDateTime.now())
                .build();
        loggingHouseMessageStore.save(message);
    }

    public void storeCustomLoggingHouseEvent(CustomLoggingHouseEvent event) {
        monitor.info("Storing CustomLoggingHouseEvent to send to LoggingHouse");

        var message = LoggingHouseMessage.Builder.newInstance()
                .eventType(event.getClass().getSimpleName())
                .eventId(event.getEventId())
                .eventToLog(event.getMessageBody())
                .createProcess(false)
                .processId(event.getProcessId())
                .status(LoggingHouseMessageStatus.PENDING)
                .createdAt(ZonedDateTime.now())
                .build();
        loggingHouseMessageStore.save(message);
    }
}