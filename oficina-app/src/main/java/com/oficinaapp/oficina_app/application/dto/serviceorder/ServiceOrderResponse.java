package com.oficinaapp.oficina_app.application.dto.serviceorder;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.model.ServiceOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceOrderResponse(

        Long id,
        String number,
        ServiceOrderStatus status,
        Long customerId,
        String customerName,
        Long vehicleId,
        String vehicleDescription,
        String summary,
        Long assignedEmployeeId,
        String assignedEmployeeName,
        List<ServiceOrderItemResponse> items,
        BigDecimal total,
        LocalDateTime openedAt,
        LocalDateTime closedAt

) {

    public static ServiceOrderResponse from(ServiceOrder order) {
        return new ServiceOrderResponse(
                order.getId(),
                order.getNumber(),
                order.getStatus(),
                order.getCustomerId(),
                order.getCustomerName(),
                order.getVehicleId(),
                order.getVehicleDescription(),
                order.getSummary(),
                order.getAssignedEmployeeId(),
                order.getAssignedEmployeeName(),
                order.getItems().stream().map(ServiceOrderItemResponse::from).toList(),
                order.getTotalAmount(),
                order.getOpenedAt(),
                order.getClosedAt()
        );
    }
}
