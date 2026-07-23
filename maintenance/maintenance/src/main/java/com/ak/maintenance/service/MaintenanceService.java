package com.ak.maintenance.service;

import com.ak.maintenance.entity.Maintenance;
import com.ak.maintenance.exception.MaintenanceNotFoundException;
import com.ak.maintenance.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository repository;

    public Maintenance create(Maintenance maintenance) {
        return repository.save(maintenance);
    }

    public List<Maintenance> getAll() {
        return repository.findAll();
    }

    public Maintenance getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new MaintenanceNotFoundException("Maintenance Not Found"));
    }

    public Maintenance update(Long id, Maintenance maintenance) {

        Maintenance existing = getById(id);

        existing.setSatelliteId(maintenance.getSatelliteId());
        existing.setEngineerName(maintenance.getEngineerName());
        existing.setScheduleDate(maintenance.getScheduleDate());
        existing.setRemarks(maintenance.getRemarks());
        existing.setStatus(maintenance.getStatus());

        return repository.save(existing);
    }

    public void delete(Long id) {

        Maintenance maintenance = getById(id);

        repository.delete(maintenance);
    }
}