package com.api.alert.DTO;

import lombok.Data;

@Data
public class AlertResponseDTO {

    private Long alertId;

    private Long satelliteId;

    private String alertType;

    private String severity;

    private String message;

    private String status;
}