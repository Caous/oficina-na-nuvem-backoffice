package com.oficinaapp.oficina_app.application.dto.dashboard;

import java.math.BigDecimal;

/**
 * Raw numbers, not formatted text: currency and abbreviations belong to the
 * app, which knows the reader's language.
 */
public record DashboardMetricsResponse(

        long openOrders,
        long completedToday,
        BigDecimal monthlyRevenue,
        BigDecimal averageTicket

) {
}
