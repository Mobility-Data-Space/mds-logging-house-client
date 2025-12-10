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
 *       truzzt GmbH - adjusted for EDC 0.x
 *
 */

package com.truzzt.extension.logginghouse.client.events.messages;

import com.truzzt.extension.logginghouse.client.multipart.ExtendedMessageProtocolClearing;
import org.eclipse.edc.spi.types.domain.message.ProtocolRemoteMessage;

import java.net.URI;
import java.net.URL;

public final class LogMessage extends ProtocolRemoteMessage {
    private final String counterPartyId;
    private final URL clearingHouseLogUrl;
    private final URI connectorBaseUrl;
    private final String eventToLog;

    public LogMessage(String counterPartyId,
                      URL clearingHouseLogUrl,
                      URI connectorBaseUrl,
                      String eventToLog) {
        this.counterPartyId = counterPartyId;
        this.clearingHouseLogUrl = clearingHouseLogUrl;
        this.connectorBaseUrl = connectorBaseUrl;
        this.eventToLog = eventToLog;
    }

    @Override
    public String getProtocol() {
        return ExtendedMessageProtocolClearing.IDS_EXTENDED_PROTOCOL_CLEARING;
    }

    @Override
    public String getCounterPartyAddress() {
        return clearingHouseLogUrl.toString();
    }

    @Override
    public String getCounterPartyId() {
        return counterPartyId;
    }

    public URI getConnectorBaseUrl() {
        return connectorBaseUrl;
    }

    public String getEventToLog() {
        return eventToLog;
    }

}
