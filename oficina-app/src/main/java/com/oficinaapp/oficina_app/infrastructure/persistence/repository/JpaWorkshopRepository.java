package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.WorkshopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWorkshopRepository extends JpaRepository<WorkshopEntity, Long> {

    boolean existsByDocument(String document);
}
