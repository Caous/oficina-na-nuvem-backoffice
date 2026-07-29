package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaVehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByIdAndOwnerId(Long id, Long ownerId);

    List<VehicleEntity> findByOwnerIdOrderByBrandAscModelAsc(Long ownerId);

    boolean existsByPlate(String plate);
}
