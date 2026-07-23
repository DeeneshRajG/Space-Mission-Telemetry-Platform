package com.api.satellite.service.impl;

import com.api.satellite.dto.SatelliteRequestDTO;
import com.api.satellite.dto.SatelliteResponseDTO;
import com.api.satellite.entity.Satellite;
import com.api.satellite.repository.SatelliteRepository;
import com.api.satellite.service.SatelliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SatelliteServiceImpl implements SatelliteService {

    private final SatelliteRepository repository;

    @Override
    public SatelliteResponseDTO createSatellite(SatelliteRequestDTO dto) {

        Satellite satellite = Satellite.builder()
                .name(dto.getName())
                .missionName(dto.getMissionName())
                .launchDate(dto.getLaunchDate())
                .orbitType(dto.getOrbitType())
                .status(dto.getStatus())
                .build();

        Satellite saved = repository.save(satellite);

        return SatelliteResponseDTO.builder()
                .satelliteId(saved.getSatelliteId())
                .name(saved.getName())
                .missionName(saved.getMissionName())
                .launchDate(saved.getLaunchDate())
                .orbitType(saved.getOrbitType())
                .status(saved.getStatus())
                .build();
    }
}