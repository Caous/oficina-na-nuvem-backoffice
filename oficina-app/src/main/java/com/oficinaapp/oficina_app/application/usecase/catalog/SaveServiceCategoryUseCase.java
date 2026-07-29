package com.oficinaapp.oficina_app.application.usecase.catalog;

import com.oficinaapp.oficina_app.application.dto.catalog.ServiceCategoryRequest;
import com.oficinaapp.oficina_app.application.dto.catalog.ServiceCategoryResponse;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.ServiceCategory;
import com.oficinaapp.oficina_app.domain.repository.ServiceCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Creates when {@code categoryId} is null and updates otherwise — the two paths
 * differ only in where the previous values come from.
 */
@Service
public class SaveServiceCategoryUseCase {

    private final ServiceCategoryRepository categories;

    public SaveServiceCategoryUseCase(ServiceCategoryRepository categories) {
        this.categories = categories;
    }

    @Transactional
    public ServiceCategoryResponse execute(Long categoryId, ServiceCategoryRequest request, Long workshopId) {
        ServiceCategory category = categoryId == null
                ? ServiceCategory.builder().workshopId(workshopId).build()
                : findOwned(categoryId, workshopId);

        ServiceCategory saved = categories.save(category.toBuilder()
                .name(request.name().trim())
                .style(request.styleOrDefault())
                .build());

        return ServiceCategoryResponse.from(saved);
    }

    private ServiceCategory findOwned(Long categoryId, Long workshopId) {
        return categories.findByIdAndWorkshopId(categoryId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Service category"));
    }
}
