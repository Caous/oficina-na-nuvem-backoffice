package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.WorkshopService;
import com.oficinaapp.oficina_app.domain.repository.WorkshopServiceRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.WorkshopServiceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkshopServiceRepositoryAdapter implements WorkshopServiceRepository {

    private final JpaWorkshopServiceRepository jpaRepository;

    public WorkshopServiceRepositoryAdapter(JpaWorkshopServiceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public WorkshopService save(WorkshopService service) {
        return WorkshopServiceMapper.toDomain(jpaRepository.save(WorkshopServiceMapper.toEntity(service)));
    }

    @Override
    public Optional<WorkshopService> findByIdAndWorkshopId(Long id, Long workshopId) {
        return jpaRepository.findByIdAndWorkshopId(id, workshopId).map(WorkshopServiceMapper::toDomain);
    }

    @Override
    public List<WorkshopService> findByWorkshopId(Long workshopId) {
        return jpaRepository.findByWorkshopIdOrderByNameAsc(workshopId).stream()
                .map(WorkshopServiceMapper::toDomain)
                .toList();
    }

    @Override
    public List<WorkshopService> findByIdInAndWorkshopId(List<Long> ids, Long workshopId) {
        return jpaRepository.findByIdInAndWorkshopId(ids, workshopId).stream()
                .map(WorkshopServiceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return jpaRepository.existsByCategoryId(categoryId);
    }

    @Override
    public void delete(WorkshopService service) {
        jpaRepository.deleteById(service.getId());
    }
}
