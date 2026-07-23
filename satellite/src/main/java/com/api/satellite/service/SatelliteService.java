package com.api.satellite.service;

import com.api.satellite.dto.SatelliteRequestDTO;
import com.api.satellite.dto.SatelliteResponseDTO;

public interface SatelliteService {

    SatelliteResponseDTO createSatellite(SatelliteRequestDTO dto);

}