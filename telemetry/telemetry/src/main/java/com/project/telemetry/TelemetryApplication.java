package com.project.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelemetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(telemetryapplication.class, args);
    }

    @SpringBootApplication
    public static class telemetryapplication {

        public static void main(String[] args) {
            SpringApplication.run(telemetryapplication.class, args);
        }

    }
}
