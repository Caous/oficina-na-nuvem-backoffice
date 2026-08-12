package com.oficinaapp.oficina_app.application.dto.catalog;

import com.oficinaapp.oficina_app.domain.model.WorkshopService;

import java.math.BigDecimal;

public record WorkshopServiceResponse(

        Long id,
        Long categoryId,
        String name,
        String description,
        BigDecimal price,
        BigDecimal maxDiscountPercent,

        /** Computed here so the app never repeats the discount maths. */
        BigDecimal minimumPrice

) {

    public static WorkshopServiceResponse from(WorkshopService service) {
        return new WorkshopServiceResponse(
                service.getId(),
                service.getCategoryId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getMaxDiscountPercent(),
                service.minimumPrice()
        );
    }
}
