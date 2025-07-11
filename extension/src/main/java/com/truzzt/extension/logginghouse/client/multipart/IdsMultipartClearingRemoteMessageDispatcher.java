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

package com.truzzt.extension.logginghouse.client.multipart;

import com.truzzt.extension.logginghouse.client.multipart.ids.multipart.IdsMultipartRemoteMessageDispatcher;
import com.truzzt.extension.logginghouse.client.multipart.ids.multipart.IdsMultipartSender;

public class IdsMultipartClearingRemoteMessageDispatcher extends IdsMultipartRemoteMessageDispatcher {

    public IdsMultipartClearingRemoteMessageDispatcher(IdsMultipartSender idsMultipartSender) {
        super(idsMultipartSender);
    }
}