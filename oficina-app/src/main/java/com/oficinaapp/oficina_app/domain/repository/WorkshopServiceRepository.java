package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.model.WorkshopService;

import java.util.List;
import java.util.Optional;

public interface WorkshopServiceRepository {

    WorkshopService save(WorkshopService service);

    Optional<WorkshopService> findByIdAndWorkshopId(Long id, Long workshopId);

    List<WorkshopService> findByWorkshopId(Long workshopId);

    List<WorkshopService> findByIdInAndWorkshopId(List<Long> ids, Long workshopId);

    boolean existsByCategoryId(Long categoryId);

    void delete(WorkshopService service);
}
