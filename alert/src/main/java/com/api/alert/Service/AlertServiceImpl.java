package com.api.alert.Service;

import com.api.alert.DTO.AlertRequestDTO;
import com.api.alert.DTO.AlertResponseDTO;
import com.api.alert.Entity.Alert;
import com.api.alert.Execption.AlertNotFoundException;
import com.api.alert.Repository.AlertRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository repository;

    public AlertServiceImpl(AlertRepository repository) {
        this.repository = repository;
    }

    @Override
    public AlertResponseDTO createAlert(AlertRequestDTO dto) {

        Alert alert = new Alert();

        BeanUtils.copyProperties(dto, alert);

        alert.setStatus("OPEN");

        Alert saved = repository.save(alert);

        AlertResponseDTO response = new AlertResponseDTO();

        BeanUtils.copyProperties(saved, response);

        return response;
    }

    @Override
    public List<AlertResponseDTO> getAllAlerts() {

        return repository.findAll().stream().map(alert -> {

            AlertResponseDTO dto = new AlertResponseDTO();

            BeanUtils.copyProperties(alert, dto);

            return dto;

        }).collect(Collectors.toList());

    }

    @Override
    public AlertResponseDTO resolveAlert(Long id) {

        Alert alert = repository.findById(id)
                .orElseThrow(() -> new AlertNotFoundException("Alert not found"));

        alert.setStatus("RESOLVED");

        Alert saved = repository.save(alert);

        AlertResponseDTO dto = new AlertResponseDTO();

        BeanUtils.copyProperties(saved, dto);

        return dto;
    }
}