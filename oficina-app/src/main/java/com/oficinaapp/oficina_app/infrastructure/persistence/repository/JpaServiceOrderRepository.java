package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ServiceOrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaServiceOrderRepository extends JpaRepository<ServiceOrderEntity, Long> {

    Optional<ServiceOrderEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    @Query("""
            select o from ServiceOrderEntity o
            where o.workshopId = :workshopId
              and (:status is null or o.status = :status)
            order by o.openedAt desc
            """)
    List<ServiceOrderEntity> search(
            @Param("workshopId") Long workshopId,
            @Param("status") ServiceOrderStatus status
    );

    List<ServiceOrderEntity> findByWorkshopIdOrderByOpenedAtDesc(Long workshopId, Pageable pageable);

    long countByWorkshopIdAndStatusIn(Long workshopId, List<ServiceOrderStatus> statuses);

    List<ServiceOrderEntity> findByWorkshopIdAndClosedAtBetween(Long workshopId, LocalDateTime from, LocalDateTime to);

    long countByWorkshopId(Long workshopId);
}
