package com.project.telemetry.exception;

public class TelemetryNotFoundException extends RuntimeException {

    public TelemetryNotFoundException(String message) {
        super(message);
    }
}