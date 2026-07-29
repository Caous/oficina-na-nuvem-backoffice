package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.model.Workshop;

import java.util.List;
import java.util.Optional;

public interface WorkshopRepository {

    Workshop save(Workshop workshop);

    Optional<Workshop> findById(Long id);

    /** Used to name the sellers of a marketplace page in a single query. */
    List<Workshop> findAllById(List<Long> ids);

    boolean existsByDocument(String document);
}
