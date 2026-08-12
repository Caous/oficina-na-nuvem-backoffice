package com.oficinaapp.oficina_app.application.usecase.catalog;

import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.WorkshopService;
import com.oficinaapp.oficina_app.domain.repository.WorkshopServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteWorkshopServiceUseCase {

    private final WorkshopServiceRepository services;

    public DeleteWorkshopServiceUseCase(WorkshopServiceRepository services) {
        this.services = services;
    }

    @Transactional
    public void execute(Long serviceId, Long workshopId) {
        WorkshopService service = services.findByIdAndWorkshopId(serviceId, workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Service"));

        services.delete(service);
    }
}
