package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.WorkshopCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaWorkshopCustomerRepository extends JpaRepository<WorkshopCustomerEntity, Long> {

    boolean existsByWorkshopIdAndCustomerId(Long workshopId, Long customerId);

    @Query("select link.customerId from WorkshopCustomerEntity link where link.workshopId = ?1")
    List<Long> findCustomerIdsOf(Long workshopId);
}
