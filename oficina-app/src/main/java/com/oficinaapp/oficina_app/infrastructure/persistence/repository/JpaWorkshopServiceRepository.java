package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.WorkshopServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaWorkshopServiceRepository extends JpaRepository<WorkshopServiceEntity, Long> {

    Optional<WorkshopServiceEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    List<WorkshopServiceEntity> findByWorkshopIdOrderByNameAsc(Long workshopId);

    List<WorkshopServiceEntity> findByIdInAndWorkshopId(List<Long> ids, Long workshopId);

    boolean existsByCategoryId(Long categoryId);
}
