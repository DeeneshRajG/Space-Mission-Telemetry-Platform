package com.ak.maintenance.controller;

import com.ak.maintenance.entity.Maintenance;
import com.ak.maintenance.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService service;

    @PostMapping
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return service.create(maintenance);
    }

    @GetMapping
    public List<Maintenance> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Maintenance getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Maintenance update(@PathVariable Long id,
                              @RequestBody Maintenance maintenance) {

        return service.update(id, maintenance);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        service.delete(id);

        return "Maintenance Deleted Successfully";
    }
}