package com.oficinaapp.oficina_app.application.dto.catalog;

import com.oficinaapp.oficina_app.domain.enums.ServiceCategoryStyle;
import jakarta.validation.constraints.NotBlank;

public record ServiceCategoryRequest(

        @NotBlank String name,

        /** Optional; falls back to {@code GENERIC}. */
        ServiceCategoryStyle style

) {

    public ServiceCategoryStyle styleOrDefault() {
        return style == null ? ServiceCategoryStyle.GENERIC : style;
    }
}
