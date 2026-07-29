package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);

    Optional<Vehicle> findByIdAndOwnerId(Long id, Long ownerId);

    List<Vehicle> findByOwnerId(Long ownerId);

    boolean existsByPlate(String plate);

    void delete(Vehicle vehicle);
}
