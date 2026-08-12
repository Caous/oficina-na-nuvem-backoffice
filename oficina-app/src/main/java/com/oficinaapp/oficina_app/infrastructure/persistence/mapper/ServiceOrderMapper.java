package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.ServiceOrder;
import com.oficinaapp.oficina_app.domain.model.ServiceOrderItem;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ServiceOrderEntity;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ServiceOrderItemEntity;

import java.util.List;

public final class ServiceOrderMapper {

    private ServiceOrderMapper() {
    }

    public static ServiceOrderEntity toEntity(ServiceOrder order) {
        ServiceOrderEntity entity = new ServiceOrderEntity();

        entity.setId(order.getId());
        entity.setWorkshopId(order.getWorkshopId());
        entity.setNumber(order.getNumber());
        entity.setStatus(order.getStatus());
        entity.setCustomerId(order.getCustomerId());
        entity.setCustomerName(order.getCustomerName());
        entity.setVehicleId(order.getVehicleId());
        entity.setVehicleDescription(order.getVehicleDescription());
        entity.setSummary(order.getSummary());
        entity.setAssignedEmployeeId(order.getAssignedEmployeeId());
        entity.setAssignedEmployeeName(order.getAssignedEmployeeName());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setOpenedAt(order.getOpenedAt());
        entity.setClosedAt(order.getClosedAt());
        entity.setItems(toItemEntities(order.getItems()));

        return entity;
    }

    public static ServiceOrder toDomain(ServiceOrderEntity entity) {
        return ServiceOrder.builder()
                .id(entity.getId())
                .workshopId(entity.getWorkshopId())
                .number(entity.getNumber())
                .status(entity.getStatus())
                .customerId(entity.getCustomerId())
                .customerName(entity.getCustomerName())
                .vehicleId(entity.getVehicleId())
                .vehicleDescription(entity.getVehicleDescription())
                .summary(entity.getSummary())
                .assignedEmployeeId(entity.getAssignedEmployeeId())
                .assignedEmployeeName(entity.getAssignedEmployeeName())
                .totalAmount(entity.getTotalAmount())
                .openedAt(entity.getOpenedAt())
                .closedAt(entity.getClosedAt())
                .items(toDomainItems(entity.getItems()))
                .build();
    }

    private static List<ServiceOrderItemEntity> toItemEntities(List<ServiceOrderItem> items) {
        return items.stream().map(item -> {
            ServiceOrderItemEntity entity = new ServiceOrderItemEntity();

            entity.setId(item.getId());
            entity.setServiceId(item.getServiceId());
            entity.setServiceName(item.getServiceName());
            entity.setPrice(item.getPrice());

            return entity;
        }).toList();
    }

    private static List<ServiceOrderItem> toDomainItems(List<ServiceOrderItemEntity> entities) {
        return entities.stream()
                .map(entity -> ServiceOrderItem.builder()
                        .id(entity.getId())
                        .serviceId(entity.getServiceId())
                        .serviceName(entity.getServiceName())
                        .price(entity.getPrice())
                        .build())
                .toList();
    }
}
