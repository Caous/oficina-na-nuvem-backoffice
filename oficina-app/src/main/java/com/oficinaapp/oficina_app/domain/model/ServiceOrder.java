package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
public class ServiceOrder {

    private final Long id;
    private final Long workshopId;

    /** Human readable code shown on screen, e.g. "OS-000012". */
    private final String number;

    private final ServiceOrderStatus status;
    private final Long customerId;
    private final String customerName;
    private final Long vehicleId;
    private final String vehicleDescription;

    /** One line joining the service names, for lists. */
    private final String summary;

    private final Long assignedEmployeeId;
    private final String assignedEmployeeName;
    private final List<ServiceOrderItem> items;
    private final BigDecimal totalAmount;
    private final LocalDateTime openedAt;
    private final LocalDateTime closedAt;

    public static ServiceOrder open(
            Long workshopId,
            Long customerId,
            String customerName,
            Long vehicleId,
            String vehicleDescription,
            Long assignedEmployeeId,
            String assignedEmployeeName,
            List<ServiceOrderItem> items,
            LocalDateTime openedAt
    ) {
        return ServiceOrder.builder()
                .workshopId(workshopId)
                .status(ServiceOrderStatus.AWAITING_APPROVAL)
                .customerId(customerId)
                .customerName(customerName)
                .vehicleId(vehicleId)
                .vehicleDescription(vehicleDescription)
                .summary(summaryOf(items))
                .assignedEmployeeId(assignedEmployeeId)
                .assignedEmployeeName(assignedEmployeeName)
                .items(items)
                .totalAmount(totalOf(items))
                .openedAt(openedAt)
                .build();
    }

    /**
     * Moves the order along. A closed order never moves again — reopening it
     * would rewrite a finished history.
     */
    public ServiceOrder moveTo(ServiceOrderStatus newStatus, LocalDateTime changedAt) {
        if (status.isClosed()) {
            throw new OperationNotAllowedException("This service order is already closed.");
        }

        return toBuilder()
                .status(newStatus)
                .closedAt(newStatus.isClosed() ? changedAt : null)
                .build();
    }

    private static String summaryOf(List<ServiceOrderItem> items) {
        return items.stream()
                .map(ServiceOrderItem::getServiceName)
                .collect(Collectors.joining(" + "));
    }

    private static BigDecimal totalOf(List<ServiceOrderItem> items) {
        return items.stream()
                .map(ServiceOrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
