package com.api.alert.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertRequestDTO {

    @NotNull
    private Long satelliteId;

    @NotBlank
    private String alertType;

    @NotBlank
    private String severity;

    @NotBlank
    private String message;
}