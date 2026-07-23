package com.api.satellite.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SatelliteResponseDTO {

    private Long satelliteId;
    private String name;
    private String missionName;
    private LocalDate launchDate;
    private String orbitType;
    private String status;
}
