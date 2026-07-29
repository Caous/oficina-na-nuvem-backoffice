package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.repository.WorkshopCustomerRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.WorkshopCustomerEntity;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WorkshopCustomerRepositoryAdapter implements WorkshopCustomerRepository {

    private final JpaWorkshopCustomerRepository jpaRepository;
    private final Clock clock;

    public WorkshopCustomerRepositoryAdapter(JpaWorkshopCustomerRepository jpaRepository, Clock clock) {
        this.jpaRepository = jpaRepository;
        this.clock = clock;
    }

    @Override
    public void link(Long workshopId, Long customerId) {
        if (isLinked(workshopId, customerId)) {
            return;
        }

        WorkshopCustomerEntity link = new WorkshopCustomerEntity();

        link.setWorkshopId(workshopId);
        link.setCustomerId(customerId);
        link.setLinkedAt(LocalDateTime.now(clock));

        jpaRepository.save(link);
    }

    @Override
    public boolean isLinked(Long workshopId, Long customerId) {
        return jpaRepository.existsByWorkshopIdAndCustomerId(workshopId, customerId);
    }

    @Override
    public List<Long> findCustomerIdsOf(Long workshopId) {
        return jpaRepository.findCustomerIdsOf(workshopId);
    }
}
