package com.oficinaapp.oficina_app.application.dto.vehicle;

import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import com.oficinaapp.oficina_app.domain.model.Vehicle;

import java.math.BigDecimal;

public record VehicleResponse(

        Long id,
        Long customerId,
        VehicleType type,
        String brand,
        String model,
        String year,
        String plate,
        String fipeCode,
        BigDecimal fipeValue,
        String shortDescription

) {

    public static VehicleResponse from(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getOwnerId(),
                vehicle.getType(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getPlate(),
                vehicle.getFipeCode(),
                vehicle.getFipeValue(),
                vehicle.shortDescription()
        );
    }
}
