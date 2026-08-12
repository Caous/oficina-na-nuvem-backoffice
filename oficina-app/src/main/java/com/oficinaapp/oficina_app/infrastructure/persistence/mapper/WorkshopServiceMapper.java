package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.WorkshopService;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.WorkshopServiceEntity;

public final class WorkshopServiceMapper {

    private WorkshopServiceMapper() {
    }

    public static WorkshopServiceEntity toEntity(WorkshopService service) {
        WorkshopServiceEntity entity = new WorkshopServiceEntity();

        entity.setId(service.getId());
        entity.setWorkshopId(service.getWorkshopId());
        entity.setCategoryId(service.getCategoryId());
        entity.setName(service.getName());
        entity.setDescription(service.getDescription());
        entity.setPrice(service.getPrice());
        entity.setMaxDiscountPercent(service.getMaxDiscountPercent());

        return entity;
    }

    public static WorkshopService toDomain(WorkshopServiceEntity entity) {
        return WorkshopService.builder()
                .id(entity.getId())
                .workshopId(entity.getWorkshopId())
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .maxDiscountPercent(entity.getMaxDiscountPercent())
                .build();
    }
}
