package com.api.satellite.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SatelliteRequestDTO {

    @NotBlank(message = "Satellite name is required")
    private String name;

    @NotBlank(message = "Mission name is required")
    private String missionName;

    private LocalDate launchDate;

    private String orbitType;

    private String status;
}