package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Vehicle;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.VehicleEntity;

public final class VehicleMapper {

    private VehicleMapper() {
    }

    public static VehicleEntity toEntity(Vehicle vehicle) {
        VehicleEntity entity = new VehicleEntity();

        entity.setId(vehicle.getId());
        entity.setOwnerId(vehicle.getOwnerId());
        entity.setType(vehicle.getType());
        entity.setBrand(vehicle.getBrand());
        entity.setModel(vehicle.getModel());
        entity.setYear(vehicle.getYear());
        entity.setPlate(vehicle.getPlate());
        entity.setFipeCode(vehicle.getFipeCode());
        entity.setFipeValue(vehicle.getFipeValue());

        return entity;
    }

    public static Vehicle toDomain(VehicleEntity entity) {
        return Vehicle.builder()
                .id(entity.getId())
                .ownerId(entity.getOwnerId())
                .type(entity.getType())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .year(entity.getYear())
                .plate(entity.getPlate())
                .fipeCode(entity.getFipeCode())
                .fipeValue(entity.getFipeValue())
                .build();
    }
}
