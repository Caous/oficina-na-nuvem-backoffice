package com.oficinaapp.oficina_app.domain.enums;

import java.util.Set;

/**
 * Life of a service order. The first four are the filters the app shows;
 * {@code COMPLETED} and {@code CANCELLED} close the order and feed the
 * dashboard.
 */
public enum ServiceOrderStatus {

    AWAITING_APPROVAL,
    APPROVED,
    IN_PROGRESS,
    TESTING,
    COMPLETED,
    CANCELLED;

    private static final Set<ServiceOrderStatus> CLOSED = Set.of(COMPLETED, CANCELLED);

    public boolean isClosed() {
        return CLOSED.contains(this);
    }
}
