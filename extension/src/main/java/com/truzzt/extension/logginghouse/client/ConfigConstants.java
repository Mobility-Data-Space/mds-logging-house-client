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

    public static final String LOGGINGHOUSE_ENABLED_SETTING = "edc.logginghouse.extension.enabled";

    public static final boolean LOGGINGHOUSE_ENABLED_DEFAULT = true;

    public static final String LOGGINGHOUSE_PERSISTENCE_SETTING = "edc.logginghouse.extension.persistence";

    public static final String LOGGINGHOUSE_PERSISTENCE_SQL = "sql";
    public static final String LOGGINGHOUSE_PERSISTENCE_IN_MEMORY = "memory";

    public static final String DATASOURCE_NAME_SETTING = "edc.datasource.logginghouse.name";

    public static final String LOGGINGHOUSE_URL_SETTING = "edc.logginghouse.extension.url";

    public static final String LOGGINGHOUSE_FLYWAY_REPAIR_SETTING = "edc.logginghouse.extension.flyway.repair";
    public static final boolean LOGGINGHOUSE_FLYWAY_REPAIR_DEFAULT = false;

    public static final String LOGGINGHOUSE_FLYWAY_CLEAN_SETTING = "edc.logginghouse.extension.flyway.clean";
    public static final boolean LOGGINGHOUSE_FLYWAY_CLEAN_DEFAULT = false;

    public static final String LOGGINGHOUSE_EXTENSION_MAX_WORKERS_SETTING = "edc.logginghouse.extension.workers.max";
    public static final int LOGGINGHOUSE_EXTENSION_MAX_WORKERS_DEFAULT = 1;

    public static final String LOGGINGHOUSE_EXTENSION_WORKERS_DELAY_SETTING = "edc.logginghouse.extension.workers.delay";
    public static final int LOGGINGHOUSE_EXTENSION_WORKERS_DELAY_DEFAULT = 10;

    public static final String LOGGINGHOUSE_EXTENSION_WORKERS_PERIOD_SETTING = "edc.logginghouse.extension.workers.period";
    public static final int LOGGINGHOUSE_EXTENSION_WORKERS_PERIOD_DEFAULT = 30;

    public static final String LOGGINGHOUSE_RETRY_LIMIT_SETTING = "edc.logginghouse.extension.retryLimit";

    public static final int LOGGINGHOUSE_RETRY_LIMIT_DEFAULT = 10;
}
