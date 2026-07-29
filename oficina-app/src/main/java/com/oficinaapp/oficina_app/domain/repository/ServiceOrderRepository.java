package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.model.ServiceOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepository {

    ServiceOrder save(ServiceOrder order);

    Optional<ServiceOrder> findByIdAndWorkshopId(Long id, Long workshopId);

    /** A null status means every status. */
    List<ServiceOrder> findByWorkshopId(Long workshopId, ServiceOrderStatus status);

    List<ServiceOrder> findRecent(Long workshopId, int limit);

    long countByWorkshopIdAndStatusIn(Long workshopId, List<ServiceOrderStatus> statuses);

    /** Orders closed inside a window, used by the dashboard. */
    List<ServiceOrder> findClosedBetween(Long workshopId, LocalDateTime from, LocalDateTime to);

    long nextSequence(Long workshopId);
}
