package com.oficinaapp.oficina_app.application.usecase.customer;

import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleRequest;
import com.oficinaapp.oficina_app.application.dto.vehicle.VehicleResponse;
import com.oficinaapp.oficina_app.application.usecase.vehicle.SaveVehicleUseCase;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.repository.WorkshopCustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The counter registers a vehicle for a customer standing there — during a new
 * service order, typically. Only for customers already linked to the workshop,
 * and the vehicle lands in the customer's own garage.
 */
@Service
public class AddCustomerVehicleUseCase {

    private final WorkshopCustomerRepository links;
    private final SaveVehicleUseCase saveVehicle;

    public AddCustomerVehicleUseCase(WorkshopCustomerRepository links, SaveVehicleUseCase saveVehicle) {
        this.links = links;
        this.saveVehicle = saveVehicle;
    }

    @Transactional
    public VehicleResponse execute(Long customerId, VehicleRequest request, Long workshopId) {
        if (!links.isLinked(workshopId, customerId)) {
            throw new ResourceNotFoundException("Customer");
        }

        return saveVehicle.execute(null, request, customerId);
    }
}
