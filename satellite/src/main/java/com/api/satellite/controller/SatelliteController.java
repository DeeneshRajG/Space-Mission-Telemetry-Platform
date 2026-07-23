package com.api.satellite.controller;

import com.api.satellite.dto.SatelliteRequestDTO;
import com.api.satellite.dto.SatelliteResponseDTO;
import com.api.satellite.service.SatelliteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/satellites")
@RequiredArgsConstructor
public class SatelliteController {

    private final SatelliteService satelliteService;

    // Create a new Satellite
    @PostMapping
    public ResponseEntity<SatelliteResponseDTO> createSatellite(
            @Valid @RequestBody SatelliteRequestDTO dto) {

        SatelliteResponseDTO response = satelliteService.createSatellite(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}