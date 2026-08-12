package com.oficinaapp.oficina_app.application.usecase.catalog;

import com.oficinaapp.oficina_app.application.dto.catalog.WorkshopServiceResponse;
import com.oficinaapp.oficina_app.domain.repository.WorkshopServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListWorkshopServicesUseCase {

    private final WorkshopServiceRepository services;

    public ListWorkshopServicesUseCase(WorkshopServiceRepository services) {
        this.services = services;
    }

    public List<WorkshopServiceResponse> execute(Long workshopId) {
        return services.findByWorkshopId(workshopId).stream()
                .map(WorkshopServiceResponse::from)
                .toList();
    }
}
