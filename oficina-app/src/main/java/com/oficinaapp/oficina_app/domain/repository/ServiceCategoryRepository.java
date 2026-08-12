package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.model.ServiceCategory;

import java.util.List;
import java.util.Optional;

public interface ServiceCategoryRepository {

    ServiceCategory save(ServiceCategory category);

    Optional<ServiceCategory> findByIdAndWorkshopId(Long id, Long workshopId);

    List<ServiceCategory> findByWorkshopId(Long workshopId);

    void delete(ServiceCategory category);
}
