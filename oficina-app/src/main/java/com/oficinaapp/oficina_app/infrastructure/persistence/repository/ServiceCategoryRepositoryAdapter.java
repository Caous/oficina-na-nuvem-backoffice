package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.ServiceCategory;
import com.oficinaapp.oficina_app.domain.repository.ServiceCategoryRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.ServiceCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServiceCategoryRepositoryAdapter implements ServiceCategoryRepository {

    private final JpaServiceCategoryRepository jpaRepository;

    public ServiceCategoryRepositoryAdapter(JpaServiceCategoryRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ServiceCategory save(ServiceCategory category) {
        return ServiceCategoryMapper.toDomain(jpaRepository.save(ServiceCategoryMapper.toEntity(category)));
    }

    @Override
    public Optional<ServiceCategory> findByIdAndWorkshopId(Long id, Long workshopId) {
        return jpaRepository.findByIdAndWorkshopId(id, workshopId).map(ServiceCategoryMapper::toDomain);
    }

    @Override
    public List<ServiceCategory> findByWorkshopId(Long workshopId) {
        return jpaRepository.findByWorkshopIdOrderByNameAsc(workshopId).stream()
                .map(ServiceCategoryMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ServiceCategory category) {
        jpaRepository.deleteById(category.getId());
    }
}
