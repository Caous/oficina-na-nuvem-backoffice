package com.oficinaapp.oficina_app.application.usecase.vehicle;

import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.Vehicle;
import com.oficinaapp.oficina_app.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteVehicleUseCase {

    private final VehicleRepository vehicles;

    public DeleteVehicleUseCase(VehicleRepository vehicles) {
        this.vehicles = vehicles;
    }

    @Transactional
    public void execute(Long vehicleId, Long ownerId) {
        Vehicle vehicle = vehicles.findByIdAndOwnerId(vehicleId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle"));

        vehicles.delete(vehicle);
    }
}
