package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Workshop;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.WorkshopEntity;

public final class WorkshopMapper {

    private WorkshopMapper() {
    }

    public static WorkshopEntity toEntity(Workshop workshop) {
        WorkshopEntity entity = new WorkshopEntity();

        entity.setId(workshop.getId());
        entity.setTradeName(workshop.getTradeName());
        entity.setDocument(workshop.getDocument());
        entity.setEmail(workshop.getEmail());
        entity.setPhone(workshop.getPhone());
        entity.setAddress(AddressMapper.toEmbeddable(workshop.getAddress()));
        entity.setEnabled(workshop.isEnabled());
        entity.setCreatedAt(workshop.getCreatedAt());
        entity.setUpdatedAt(workshop.getUpdatedAt());

        return entity;
    }

    public static Workshop toDomain(WorkshopEntity entity) {
        return Workshop.builder()
                .id(entity.getId())
                .tradeName(entity.getTradeName())
                .document(entity.getDocument())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(AddressMapper.toDomain(entity.getAddress()))
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
