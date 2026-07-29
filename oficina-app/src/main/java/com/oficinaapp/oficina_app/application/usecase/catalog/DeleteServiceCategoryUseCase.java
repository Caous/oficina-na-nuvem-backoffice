package com.oficinaapp.oficina_app.application.usecase.catalog;

import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.ServiceCategory;
import com.oficinaapp.oficina_app.domain.repository.ServiceCategoryRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteServiceCategoryUseCase {

    private final ServiceCategoryRepository categories;
    private final WorkshopServiceRepository services;

    public DeleteServiceCategoryUseCase(ServiceCategoryRepository categories, WorkshopServiceRepository services) {
        this.categories = categories;
        this.services = services;
    }

    @Transactional
    public void execute(Long categoryId, Long workshopId) {
        ServiceCategory category = categories.findByIdAndWorkshopId(categoryId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Service category"));

        if (services.existsByCategoryId(categoryId)) {
            throw new OperationNotAllowedException("Move or remove the services of this category first.");
        }

        categories.delete(category);
    }
}
