package com.truzzt.extension.logginghouse.client.spi.types;

import org.eclipse.edc.spi.EdcException;

public enum LoggingHouseMessageStatus {
    PENDING("P"), SENT("S"), FAILED("F");

    private final String code;

    LoggingHouseMessageStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static LoggingHouseMessageStatus codeOf(String code) {
        return switch (code) {
            case "P" -> PENDING;
            case "S" -> SENT;
            case "F" -> FAILED;
            default -> throw new EdcException("Invalid status code " + code);
        };
    }
}
