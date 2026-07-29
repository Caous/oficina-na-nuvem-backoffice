package com.oficinaapp.oficina_app.application.usecase.customer;

import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleResponse;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.repository.VehicleRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The garage of a customer, seen from the counter — only for customers already
 * linked to this workshop.
 */
@Service
public class ListCustomerVehiclesUseCase {

    private final WorkshopCustomerRepository links;
    private final VehicleRepository vehicles;

    public ListCustomerVehiclesUseCase(WorkshopCustomerRepository links, VehicleRepository vehicles) {
        this.links = links;
        this.vehicles = vehicles;
    }

    public List<VehicleResponse> execute(Long customerId, Long workshopId) {
        if (!links.isLinked(workshopId, customerId)) {
            throw new ResourceNotFoundException("Customer");
        }

        return vehicles.findByOwnerId(customerId).stream()
                .map(VehicleResponse::from)
                .toList();
    }
}
