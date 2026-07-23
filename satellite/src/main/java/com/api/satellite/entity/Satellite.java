package com.api.satellite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "satellite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Satellite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long satelliteId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String missionName;

    private LocalDate launchDate;

    private String orbitType;

    private String status;
}
