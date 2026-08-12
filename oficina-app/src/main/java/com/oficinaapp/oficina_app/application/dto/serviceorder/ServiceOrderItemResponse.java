package com.oficinaapp.oficina_app.application.dto.serviceorder;

import com.oficinaapp.oficina_app.domain.model.ServiceOrderItem;

import java.math.BigDecimal;

public record ServiceOrderItemResponse(

        Long serviceId,
        String serviceName,
        BigDecimal price

) {

    public static ServiceOrderItemResponse from(ServiceOrderItem item) {
        return new ServiceOrderItemResponse(item.getServiceId(), item.getServiceName(), item.getPrice());
    }
}
