package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.model.ServiceOrder;
import com.oficinaapp.oficina_app.domain.repository.ServiceOrderRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.ServiceOrderMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ServiceOrderRepositoryAdapter implements ServiceOrderRepository {

    private final JpaServiceOrderRepository jpaRepository;

    public ServiceOrderRepositoryAdapter(JpaServiceOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ServiceOrder save(ServiceOrder order) {
        return ServiceOrderMapper.toDomain(jpaRepository.save(ServiceOrderMapper.toEntity(order)));
    }

    @Override
    public Optional<ServiceOrder> findByIdAndWorkshopId(Long id, Long workshopId) {
        return jpaRepository.findByIdAndWorkshopId(id, workshopId).map(ServiceOrderMapper::toDomain);
    }

    @Override
    public List<ServiceOrder> findByWorkshopId(Long workshopId, ServiceOrderStatus status) {
        return jpaRepository.search(workshopId, status).stream()
                .map(ServiceOrderMapper::toDomain)
                .toList();
    }

    @Override
    public List<ServiceOrder> findRecent(Long workshopId, int limit) {
        return jpaRepository.findByWorkshopIdOrderByOpenedAtDesc(workshopId, PageRequest.of(0, limit)).stream()
                .map(ServiceOrderMapper::toDomain)
                .toList();
    }

    @Override
    public long countByWorkshopIdAndStatusIn(Long workshopId, List<ServiceOrderStatus> statuses) {
        return jpaRepository.countByWorkshopIdAndStatusIn(workshopId, statuses);
    }

    @Override
    public List<ServiceOrder> findClosedBetween(Long workshopId, LocalDateTime from, LocalDateTime to) {
        return jpaRepository.findByWorkshopIdAndClosedAtBetween(workshopId, from, to).stream()
                .map(ServiceOrderMapper::toDomain)
                .toList();
    }

    @Override
    public long nextSequence(Long workshopId) {
        return jpaRepository.countByWorkshopId(workshopId) + 1;
    }
}
