package com.ak.maintenance.exception;

public class MaintenanceNotFoundException extends RuntimeException {

    public MaintenanceNotFoundException(String message) {
        super(message);
    }
}