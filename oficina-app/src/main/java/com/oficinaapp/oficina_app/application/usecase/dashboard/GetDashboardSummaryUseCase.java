package com.oficinaapp.oficina_app.application.usecase.dashboard;

import com.oficinaapp.oficina_app.application.dto.dashboard.DailyServiceCountResponse;
import com.oficinaapp.oficina_app.application.dto.dashboard.DashboardMetricsResponse;
import com.oficinaapp.oficina_app.application.dto.dashboard.DashboardSummaryResponse;
import com.oficinaapp.oficina_app.application.dto.serviceorder.ServiceOrderResponse;
import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.exception.ResourceNotFoundException;
import com.oficinaapp.oficina_app.domain.model.ServiceOrder;
import com.oficinaapp.oficina_app.domain.model.Workshop;
import com.oficinaapp.oficina_app.domain.repository.ServiceOrderRepository;
import com.oficinaapp.oficina_app.domain.repository.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Read-only view assembled from the service orders. It stores nothing of its
 * own, so the dashboard can never disagree with the orders screen.
 */
@Service
public class GetDashboardSummaryUseCase {

    private static final List<ServiceOrderStatus> OPEN_STATUSES = List.of(
            ServiceOrderStatus.AWAITING_APPROVAL,
            ServiceOrderStatus.APPROVED,
            ServiceOrderStatus.IN_PROGRESS,
            ServiceOrderStatus.TESTING
    );

    private static final int WEEK_LENGTH = 7;
    private static final int RECENT_ORDERS = 5;

    private final ServiceOrderRepository orders;
    private final WorkshopRepository workshops;
    private final Clock clock;

    public GetDashboardSummaryUseCase(
            ServiceOrderRepository orders,
            WorkshopRepository workshops,
            Clock clock
    ) {
        this.orders = orders;
        this.workshops = workshops;
        this.clock = clock;
    }

    public DashboardSummaryResponse execute(Long workshopId) {
        Workshop workshop = workshops.findById(workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop"));

        LocalDate today = LocalDate.now(clock);
        List<ServiceOrder> completedThisMonth = completedBetween(workshopId, today.withDayOfMonth(1), today);
        List<ServiceOrder> completedThisWeek = completedBetween(workshopId, today.minusDays(WEEK_LENGTH - 1L), today);

        return new DashboardSummaryResponse(
                workshop.getTradeName(),
                metricsOf(workshopId, completedThisMonth, today),
                weeklySeries(completedThisWeek, today),
                orders.findRecent(workshopId, RECENT_ORDERS).stream()
                        .map(ServiceOrderResponse::from)
                        .toList()
        );
    }

    private List<ServiceOrder> completedBetween(Long workshopId, LocalDate from, LocalDate to) {
        return orders.findClosedBetween(workshopId, from.atStartOfDay(), to.atTime(LocalTime.MAX)).stream()
                .filter(order -> order.getStatus() == ServiceOrderStatus.COMPLETED)
                .toList();
    }

    private DashboardMetricsResponse metricsOf(
            Long workshopId,
            List<ServiceOrder> completedThisMonth,
            LocalDate today
    ) {
        BigDecimal revenue = completedThisMonth.stream()
                .map(ServiceOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long completedToday = completedThisMonth.stream()
                .filter(order -> order.getClosedAt().toLocalDate().equals(today))
                .count();

        return new DashboardMetricsResponse(
                orders.countByWorkshopIdAndStatusIn(workshopId, OPEN_STATUSES),
                completedToday,
                revenue,
                averageOf(revenue, completedThisMonth.size())
        );
    }

    private static BigDecimal averageOf(BigDecimal revenue, int orderCount) {
        if (orderCount == 0) {
            return BigDecimal.ZERO;
        }

        return revenue.divide(BigDecimal.valueOf(orderCount), 2, RoundingMode.HALF_UP);
    }

    /** Every day of the window appears, including the ones with no work. */
    private static List<DailyServiceCountResponse> weeklySeries(List<ServiceOrder> completed, LocalDate today) {
        Map<LocalDate, Long> countByDay = completed.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getClosedAt().toLocalDate(),
                        Collectors.counting()
                ));

        return Stream.iterate(today.minusDays(WEEK_LENGTH - 1L), day -> day.plusDays(1))
                .limit(WEEK_LENGTH)
                .map(day -> new DailyServiceCountResponse(day, countByDay.getOrDefault(day, 0L)))
                .toList();
    }
}
