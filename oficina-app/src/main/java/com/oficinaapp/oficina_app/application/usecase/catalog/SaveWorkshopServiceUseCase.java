package com.oficinaapp.oficina_app.application.usecase.catalog;

import com.oficinaapp.oficina_app.application.dto.catalog.WorkshopServiceRequest;
import com.oficinaapp.oficina_app.application.dto.catalog.WorkshopServiceResponse;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.WorkshopService;
import com.oficinaapp.oficina_app.domain.repository.ServiceCategoryRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveWorkshopServiceUseCase {

    private final WorkshopServiceRepository services;
    private final ServiceCategoryRepository categories;

    public SaveWorkshopServiceUseCase(WorkshopServiceRepository services, ServiceCategoryRepository categories) {
        this.services = services;
        this.categories = categories;
    }

    @Transactional
    public WorkshopServiceResponse execute(Long serviceId, WorkshopServiceRequest request, Long workshopId) {
        requireOwnCategory(request.categoryId(), workshopId);

        WorkshopService service = serviceId == null
                ? WorkshopService.builder().workshopId(workshopId).build()
                : findOwned(serviceId, workshopId);

        WorkshopService saved = services.save(service.toBuilder()
                .categoryId(request.categoryId())
                .name(request.name().trim())
                .description(request.description())
                .price(request.price())
                .maxDiscountPercent(request.maxDiscountPercent())
                .build());

        return WorkshopServiceResponse.from(saved);
    }

    /** A service can only point at a category the same workshop owns. */
    private void requireOwnCategory(Long categoryId, Long workshopId) {
        categories.findByIdAndWorkshopId(categoryId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Service category"));
    }

    private WorkshopService findOwned(Long serviceId, Long workshopId) {
        return services.findByIdAndWorkshopId(serviceId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Service"));
    }
}
