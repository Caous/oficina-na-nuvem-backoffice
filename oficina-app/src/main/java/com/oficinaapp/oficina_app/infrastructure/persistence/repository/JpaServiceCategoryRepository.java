package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ServiceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, Long> {

    Optional<ServiceCategoryEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    List<ServiceCategoryEntity> findByWorkshopIdOrderByNameAsc(Long workshopId);
}
