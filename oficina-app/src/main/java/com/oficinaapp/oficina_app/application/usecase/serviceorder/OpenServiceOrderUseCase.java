package com.oficinaapp.oficina_app.application.usecase.serviceorder;

import com.oficinaapp.oficina_app.application.dto.serviceorder.OpenServiceOrderRequest;
import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;
import com.oficinaapp.oficina_app.application.usecase.employee.WorkshopEmployeeFinder;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.ServiceOrder;
import com.oficinaapp.oficina_app.domain.model.ServiceOrderItem;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.model.Vehicle;
import com.oficinaapp.oficina_app.domain.model.WorkshopService;
import com.oficinaapp.oficina_app.domain.repository.ServiceOrderRepository;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.domain.repository.VehicleRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopCustomerRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Opens an order for a customer of this workshop. Customer, vehicle, services
 * and mechanic are all checked against the workshop before anything is stored,
 * so an order can never mix data from two workshops.
 */
@Service
public class OpenServiceOrderUseCase {

    private static final String NUMBER_FORMAT = "OS-%06d";

    private final ServiceOrderRepository orders;
    private final WorkshopCustomerRepository links;
    private final UserAccountRepository userAccounts;
    private final VehicleRepository vehicles;
    private final WorkshopServiceRepository services;
    private final WorkshopEmployeeFinder employeeFinder;
    private final Clock clock;

    public OpenServiceOrderUseCase(
            ServiceOrderRepository orders,
            WorkshopCustomerRepository links,
            UserAccountRepository userAccounts,
            VehicleRepository vehicles,
            WorkshopServiceRepository services,
            WorkshopEmployeeFinder employeeFinder,
            Clock clock
    ) {
        this.orders = orders;
        this.links = links;
        this.userAccounts = userAccounts;
        this.vehicles = vehicles;
        this.services = services;
        this.employeeFinder = employeeFinder;
        this.clock = clock;
    }

    @Transactional
    public ServiceOrderResponse execute(OpenServiceOrderRequest request, Long workshopId) {
        UserAccount customer = requireLinkedCustomer(request.customerId(), workshopId);
        Vehicle vehicle = requireVehicleOf(request.vehicleId(), customer.getId());
        List<ServiceOrderItem> items = requireServices(request.serviceIds(), workshopId);
        UserAccount mechanic = findMechanic(request.assignedEmployeeId(), workshopId);

        ServiceOrder order = ServiceOrder.open(
                workshopId,
                customer.getId(),
                customer.getName(),
                vehicle.getId(),
                vehicle.shortDescription(),
                mechanic == null ? null : mechanic.getId(),
                mechanic == null ? null : mechanic.getName(),
                items,
                LocalDateTime.now(clock)
        );

        ServiceOrder numbered = order.toBuilder()
                .number(NUMBER_FORMAT.formatted(orders.nextSequence(workshopId)))
                .build();

        return ServiceOrderResponse.from(orders.save(numbered));
    }

    private UserAccount requireLinkedCustomer(Long customerId, Long workshopId) {
        if (!links.isLinked(workshopId, customerId)) {
            throw new ResourceNotFoundException("Customer");
        }

        return userAccounts.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer"));
    }

    private Vehicle requireVehicleOf(Long vehicleId, Long customerId) {
        return vehicles.findByIdAndOwnerId(vehicleId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle"));
    }

    private List<ServiceOrderItem> requireServices(List<Long> serviceIds, Long workshopId) {
        List<WorkshopService> found = services.findByIdInAndWorkshopId(serviceIds, workshopId);

        if (found.size() != serviceIds.stream().distinct().count()) {
            throw new OperationNotAllowedException("Some of the chosen services do not belong to this workshop.");
        }

        return found.stream()
                .map(service -> ServiceOrderItem.builder()
                        .serviceId(service.getId())
                        .serviceName(service.getName())
                        .price(service.getPrice())
                        .build())
                .toList();
    }

    private UserAccount findMechanic(Long employeeId, Long workshopId) {
        return employeeId == null ? null : employeeFinder.findInWorkshop(employeeId, workshopId);
    }
}
