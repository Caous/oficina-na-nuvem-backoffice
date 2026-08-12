package com.oficinaapp.oficina_app.application.dto.catalog;

import com.oficinaapp.oficina_app.domain.enums.ServiceCategoryStyle;
import com.oficinaapp.oficina_app.domain.model.ServiceCategory;

public record ServiceCategoryResponse(

        Long id,
        String name,
        ServiceCategoryStyle style

) {

    public static ServiceCategoryResponse from(ServiceCategory category) {
        return new ServiceCategoryResponse(category.getId(), category.getName(), category.getStyle());
    }
}
