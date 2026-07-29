package com.oficinaapp.oficina_app.application.usecase.serviceorder;

import com.oficinaapp.oficina_app.application.dto.serviceorder.ChangeStatusRequest;
import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.ServiceOrder;
import com.oficinaapp.oficina_app.domain.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class ChangeServiceOrderStatusUseCase {

    private final ServiceOrderRepository orders;
    private final Clock clock;

    public ChangeServiceOrderStatusUseCase(ServiceOrderRepository orders, Clock clock) {
        this.orders = orders;
        this.clock = clock;
    }

    @Transactional
    public ServiceOrderResponse execute(Long orderId, ChangeStatusRequest request, Long workshopId) {
        ServiceOrder order = orders.findByIdAndWorkshopId(orderId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Service order"));

        ServiceOrder moved = order.moveTo(request.status(), LocalDateTime.now(clock));

        return ServiceOrderResponse.from(orders.save(moved));
    }
}
