package com.oficinaapp.oficina_app.application.usecase.serviceorder;

import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class GetServiceOrderUseCase {

    private final ServiceOrderRepository orders;

    public GetServiceOrderUseCase(ServiceOrderRepository orders) {
        this.orders = orders;
    }

    public ServiceOrderResponse execute(Long orderId, Long workshopId) {
        return orders.findByIdAndWorkshopId(orderId, workshopId)
                .map(ServiceOrderResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Service order"));
    }
}
