package com.api.alert.Controller;

import com.api.alert.DTO.AlertRequestDTO;
import com.api.alert.DTO.AlertResponseDTO;
import com.api.alert.Service.AlertService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlertResponseDTO createAlert(@Valid @RequestBody AlertRequestDTO dto) {

        return service.createAlert(dto);

    }

    @GetMapping
    public List<AlertResponseDTO> getAllAlerts() {

        return service.getAllAlerts();

    }

    @PutMapping("/{id}/resolve")
    public AlertResponseDTO resolveAlert(@PathVariable Long id) {

        return service.resolveAlert(id);

    }
}