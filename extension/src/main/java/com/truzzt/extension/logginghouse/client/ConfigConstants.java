/*
 *  Copyright (c) 2024 truzzt GmbH
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       truzzt GmbH - Initial implementation
 *
 */

package com.truzzt.extension.logginghouse.client;

public class ConfigConstants {

    static final String LOGGINGHOUSE_ENABLED_SETTING = "edc.logginghouse.extension.enabled";

    static final boolean LOGGINGHOUSE_ENABLED_DEFAULT = true;

    static final String LOGGINGHOUSE_URL_SETTING = "edc.logginghouse.extension.url";

    static final String LOGGINGHOUSE_FLYWAY_REPAIR_SETTING = "edc.logginghouse.extension.flyway.repair";

    static final boolean LOGGINGHOUSE_FLYWAY_REPAIR_DEFAULT = false;

    static final String LOGGINGHOUSE_FLYWAY_CLEAN_SETTING = "edc.logginghouse.extension.flyway.clean";

    static final boolean LOGGINGHOUSE_FLYWAY_CLEAN_DEFAULT = false;

    static final String LOGGINGHOUSE_EXTENSION_MAX_WORKERS_SETTING = "edc.logginghouse.extension.workers.max";

    static final int LOGGINGHOUSE_EXTENSION_MAX_WORKERS_DEFAULT = 1;

    static final String LOGGINGHOUSE_EXTENSION_WORKERS_DELAY_SETTING = "edc.logginghouse.extension.workers.delay";

    static final int LOGGINGHOUSE_EXTENSION_WORKERS_DELAY_DEFAULT = 10;

    static final String LOGGINGHOUSE_EXTENSION_WORKERS_PERIOD_SETTING = "edc.logginghouse.extension.workers.period";

    static final int LOGGINGHOUSE_EXTENSION_WORKERS_PERIOD_DEFAULT = 30;
}
