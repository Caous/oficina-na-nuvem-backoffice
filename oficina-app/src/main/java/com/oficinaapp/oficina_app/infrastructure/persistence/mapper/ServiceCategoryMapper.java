package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.ServiceCategory;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ServiceCategoryEntity;

public final class ServiceCategoryMapper {

    private ServiceCategoryMapper() {
    }

    public static ServiceCategoryEntity toEntity(ServiceCategory category) {
        ServiceCategoryEntity entity = new ServiceCategoryEntity();

        entity.setId(category.getId());
        entity.setWorkshopId(category.getWorkshopId());
        entity.setName(category.getName());
        entity.setStyle(category.getStyle());

        return entity;
    }

    public static ServiceCategory toDomain(ServiceCategoryEntity entity) {
        return ServiceCategory.builder()
                .id(entity.getId())
                .workshopId(entity.getWorkshopId())
                .name(entity.getName())
                .style(entity.getStyle())
                .build();
    }
}
