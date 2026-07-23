package com.project.telemetry.repository;

import com.project.telemetry.entity.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

    List<Telemetry> findBySatelliteId(Long satelliteId);

    Optional<Telemetry> findTopBySatelliteIdOrderByTimestampDesc(Long satelliteId);
}