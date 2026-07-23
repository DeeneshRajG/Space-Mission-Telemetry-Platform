package com.project.telemetry.controller;

import com.project.telemetry.TelemetryApplication;
import com.project.telemetry.dto.TelemetryResponseDTO;
import com.project.telemetry.service.TelemetryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    @Autowired
    private TelemetryService service;

    @PostMapping
    public ResponseEntity<TelemetryResponseDTO> addTelemetry(
            @Valid @RequestBody TelemetryApplication.telemetryapplication dto) {

        return ResponseEntity.ok(service.saveTelemetry(dto));
    }

    @GetMapping("/{satelliteId}")
    public ResponseEntity<List<TelemetryResponseDTO>> getTelemetryHistory(
            @PathVariable Long satelliteId) {

        return ResponseEntity.ok(service.getTelemetryHistory(satelliteId));
    }

    @GetMapping("/latest/{satelliteId}")
    public ResponseEntity<TelemetryResponseDTO> getLatestTelemetry(
            @PathVariable Long satelliteId) {

        return ResponseEntity.ok(service.getLatestTelemetry(satelliteId));
    }
}