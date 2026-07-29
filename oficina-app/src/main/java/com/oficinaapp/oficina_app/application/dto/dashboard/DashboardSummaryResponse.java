package com.oficinaapp.oficina_app.application.dto.dashboard;

import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;

import java.util.List;

public record DashboardSummaryResponse(

        String workshopName,
        DashboardMetricsResponse metrics,

        /** One entry per day of the last week, oldest first. */
        List<DailyServiceCountResponse> weeklyServices,

        List<ServiceOrderResponse> recentOrders

) {
}
