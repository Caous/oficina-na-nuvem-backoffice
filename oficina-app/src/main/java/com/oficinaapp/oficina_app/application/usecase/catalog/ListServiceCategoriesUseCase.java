package com.oficinaapp.oficina_app.application.usecase.catalog;

import com.oficinaapp.oficina_app.application.dto.catalog.ServiceCategoryResponse;
import com.oficinaapp.oficina_app.domain.repository.ServiceCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceCategoriesUseCase {

    private final ServiceCategoryRepository categories;

    public ListServiceCategoriesUseCase(ServiceCategoryRepository categories) {
        this.categories = categories;
    }

    public List<ServiceCategoryResponse> execute(Long workshopId) {
        return categories.findByWorkshopId(workshopId).stream()
                .map(ServiceCategoryResponse::from)
                .toList();
    }
}
