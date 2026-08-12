package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.Workshop;
import com.oficinaapp.oficina_app.domain.repository.WorkshopRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.WorkshopMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkshopRepositoryAdapter implements WorkshopRepository {

    private final JpaWorkshopRepository jpaRepository;

    public WorkshopRepositoryAdapter(JpaWorkshopRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Workshop save(Workshop workshop) {
        return WorkshopMapper.toDomain(jpaRepository.save(WorkshopMapper.toEntity(workshop)));
    }

    @Override
    public Optional<Workshop> findById(Long id) {
        return jpaRepository.findById(id).map(WorkshopMapper::toDomain);
    }

    @Override
    public List<Workshop> findAllById(List<Long> ids) {
        return jpaRepository.findAllById(ids).stream()
                .map(WorkshopMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByDocument(String document) {
        return jpaRepository.existsByDocument(document);
    }
}
