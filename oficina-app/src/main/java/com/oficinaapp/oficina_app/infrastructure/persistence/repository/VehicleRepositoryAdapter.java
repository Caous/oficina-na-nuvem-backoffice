package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.Vehicle;
import com.oficinaapp.oficina_app.domain.repository.VehicleRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.VehicleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleRepositoryAdapter implements VehicleRepository {

    private final JpaVehicleRepository jpaRepository;

    public VehicleRepositoryAdapter(JpaVehicleRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return VehicleMapper.toDomain(jpaRepository.save(VehicleMapper.toEntity(vehicle)));
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return jpaRepository.findById(id).map(VehicleMapper::toDomain);
    }

    @Override
    public Optional<Vehicle> findByIdAndOwnerId(Long id, Long ownerId) {
        return jpaRepository.findByIdAndOwnerId(id, ownerId).map(VehicleMapper::toDomain);
    }

    @Override
    public List<Vehicle> findByOwnerId(Long ownerId) {
        return jpaRepository.findByOwnerIdOrderByBrandAscModelAsc(ownerId).stream()
                .map(VehicleMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByPlate(String plate) {
        return jpaRepository.existsByPlate(plate);
    }

    @Override
    public void delete(Vehicle vehicle) {
        jpaRepository.deleteById(vehicle.getId());
    }
}
