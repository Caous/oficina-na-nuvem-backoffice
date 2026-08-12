package com.oficinaapp.oficina_app.application.usecase.serviceorder;

import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;
import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceOrdersUseCase {

    private final ServiceOrderRepository orders;

    public ListServiceOrdersUseCase(ServiceOrderRepository orders) {
        this.orders = orders;
    }

    /** A null status returns every order of the workshop. */
    public List<ServiceOrderResponse> execute(Long workshopId, ServiceOrderStatus status) {
        return orders.findByWorkshopId(workshopId, status).stream()
                .map(ServiceOrderResponse::from)
                .toList();
    }
}
