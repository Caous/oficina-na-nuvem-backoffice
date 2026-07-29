package com.oficinaapp.oficina_app.domain.repository;

import java.util.List;

/**
 * Which customers a workshop may see. A workshop never browses the whole
 * customer base: it only reaches the people it has already served or looked up
 * by document.
 */
public interface WorkshopCustomerRepository {

    void link(Long workshopId, Long customerId);

    boolean isLinked(Long workshopId, Long customerId);

    List<Long> findCustomerIdsOf(Long workshopId);
}
