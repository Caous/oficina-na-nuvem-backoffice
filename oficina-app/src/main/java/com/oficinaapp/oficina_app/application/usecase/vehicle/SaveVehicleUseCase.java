package com.oficinaapp.oficina_app.application.usecase.vehicle;

import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleRequest;
import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleResponse;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.Vehicle;
import com.oficinaapp.oficina_app.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Adds a vehicle to a garage, or edits one already there. FIPE data is only
 * kept for the types that table covers, so a jet ski never carries a stale
 * car price.
 */
@Service
public class SaveVehicleUseCase {

    private final VehicleRepository vehicles;

    public SaveVehicleUseCase(VehicleRepository vehicles) {
        this.vehicles = vehicles;
    }

    @Transactional
    public VehicleResponse execute(Long vehicleId, VehicleRequest request, Long ownerId) {
        String plate = normalizePlate(request.plate());

        Vehicle current = vehicleId == null
                ? Vehicle.builder().ownerId(ownerId).build()
                : findOwned(vehicleId, ownerId);

        rejectPlateTakenByAnotherVehicle(plate, current);

        Vehicle saved = vehicles.save(current.toBuilder()
                .type(request.type())
                .brand(request.brand().trim())
                .model(request.model().trim())
                .year(request.year().trim())
                .plate(plate)
                .fipeCode(request.type().isCoveredByFipe() ? request.fipeCode() : null)
                .fipeValue(request.type().isCoveredByFipe() ? request.fipeValue() : BigDecimal.ZERO)
                .build());

        return VehicleResponse.from(saved);
    }

    private Vehicle findOwned(Long vehicleId, Long ownerId) {
        return vehicles.findByIdAndOwnerId(vehicleId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle"));
    }

    private void rejectPlateTakenByAnotherVehicle(String plate, Vehicle current) {
        boolean samePlate = plate.equals(current.getPlate());

        if (!samePlate && vehicles.existsByPlate(plate)) {
            throw new OperationNotAllowedException("This plate is already registered.");
        }
    }

    private static String normalizePlate(String plate) {
        return plate.trim().toUpperCase(Locale.ROOT).replace("-", "");
    }
}
