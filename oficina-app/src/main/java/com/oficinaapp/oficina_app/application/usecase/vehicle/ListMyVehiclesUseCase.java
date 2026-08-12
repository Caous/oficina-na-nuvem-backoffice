package com.oficinaapp.oficina_app.application.usecase.vehicle;

import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleResponse;
import com.oficinaapp.oficina_app.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMyVehiclesUseCase {

    private final VehicleRepository vehicles;

    public ListMyVehiclesUseCase(VehicleRepository vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleResponse> execute(Long ownerId) {
        return vehicles.findByOwnerId(ownerId).stream()
                .map(VehicleResponse::from)
                .toList();
    }
}
