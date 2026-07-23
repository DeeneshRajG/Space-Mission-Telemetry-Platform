package com.project.telemetry.service;

import java.util.List;

import com.project.telemetry.TelemetryApplication;
import com.project.telemetry.dto.TelemetryResponseDTO;

public interface TelemetryService {

    TelemetryResponseDTO saveTelemetry(TelemetryApplication.telemetryapplication dto);

    List<TelemetryResponseDTO> getTelemetryHistory(Long satelliteId);

    TelemetryResponseDTO getLatestTelemetry(Long satelliteId);
}