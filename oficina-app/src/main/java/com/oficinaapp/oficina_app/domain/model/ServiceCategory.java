package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.ServiceCategoryStyle;
import lombok.Builder;
import lombok.Getter;

/**
 * Groups the services a workshop offers. Each workshop owns its own
 * categories.
 */
@Getter
@Builder(toBuilder = true)
public class ServiceCategory {

    private final Long id;
    private final Long workshopId;
    private final String name;
    private final ServiceCategoryStyle style;
}
