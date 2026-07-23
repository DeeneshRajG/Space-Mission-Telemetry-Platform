package com.api.alert.Service;


import com.api.alert.DTO.AlertRequestDTO;
import com.api.alert.DTO.AlertResponseDTO;

import java.util.List;

public interface AlertService {

    AlertResponseDTO createAlert(AlertRequestDTO dto);

    List<AlertResponseDTO> getAllAlerts();

    AlertResponseDTO resolveAlert(Long id);
}