package com.oficinaapp.oficina_app.infrastructure.persistence.entity;

import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "vehicle",
        uniqueConstraints = @UniqueConstraint(name = "uk_vehicle_plate", columnNames = "plate")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleType type;

    @Column(nullable = false, length = 60)
    private String brand;

    @Column(nullable = false, length = 120)
    private String model;

    @Column(nullable = false, length = 40)
    private String year;

    @Column(nullable = false, length = 10)
    private String plate;

    @Column(name = "fipe_code", length = 20)
    private String fipeCode;

    @Column(name = "fipe_value", precision = 12, scale = 2)
    private BigDecimal fipeValue;
}
