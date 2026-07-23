package com.project.telemetry.service;

import java.util.List;

import com.project.telemetry.TelemetryApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.telemetry.dto.TelemetryResponseDTO;
import com.project.telemetry.repository.TelemetryRepository;

@Service
public class TelemetryServiceImpl implements TelemetryService {

    @Autowired
    private TelemetryRepository repository;

    @Override
    public TelemetryResponseDTO saveTelemetry(TelemetryApplication.telemetryapplication dto) {
        // TODO: Save telemetry
        return null;
    }

    @Override
    public List<TelemetryResponseDTO> getTelemetryHistory(Long satelliteId) {
        // TODO: Get telemetry history
        return null;
    }

    @Override
    public TelemetryResponseDTO getLatestTelemetry(Long satelliteId) {
        // TODO: Get latest telemetry
        return null;
    }
}