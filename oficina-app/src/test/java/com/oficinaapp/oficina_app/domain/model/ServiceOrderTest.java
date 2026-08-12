package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ServiceOrderTest {

    private static final LocalDateTime NOW = LocalDateTime.parse("2026-03-10T12:00:00");

    @Test
    @DisplayName("opens awaiting approval, summing the services and joining their names")
    void opensAwaitingApproval() {
        ServiceOrder order = openOrder();

        assertThat(order.getStatus()).isEqualTo(ServiceOrderStatus.AWAITING_APPROVAL);
        assertThat(order.getTotalAmount()).isEqualByComparingTo("330.00");
        assertThat(order.getSummary()).isEqualTo("Troca de oleo + Alinhamento");
        assertThat(order.getClosedAt()).isNull();
    }

    @Test
    @DisplayName("stamps the closing time when it reaches a final status")
    void stampsClosingTime() {
        ServiceOrder completed = openOrder().moveTo(ServiceOrderStatus.COMPLETED, NOW.plusHours(4));

        assertThat(completed.getStatus()).isEqualTo(ServiceOrderStatus.COMPLETED);
        assertThat(completed.getClosedAt()).isEqualTo(NOW.plusHours(4));
    }

    @Test
    @DisplayName("leaves the closing time empty while the order is still running")
    void keepsClosingTimeEmptyWhileOpen() {
        ServiceOrder inProgress = openOrder().moveTo(ServiceOrderStatus.IN_PROGRESS, NOW.plusHours(1));

        assertThat(inProgress.getClosedAt()).isNull();
    }

    @Test
    @DisplayName("refuses to move an order that is already closed")
    void refusesToReopenClosedOrder() {
        ServiceOrder completed = openOrder().moveTo(ServiceOrderStatus.COMPLETED, NOW.plusHours(4));

        assertThatThrownBy(() -> completed.moveTo(ServiceOrderStatus.IN_PROGRESS, NOW.plusHours(5)))
                .isInstanceOf(OperationNotAllowedException.class);
    }

    private ServiceOrder openOrder() {
        return ServiceOrder.open(
                7L,
                2L,
                "Joao Pereira",
                3L,
                "Honda Civic • ABC1D23",
                null,
                null,
                List.of(
                        ServiceOrderItem.builder().serviceId(1L).serviceName("Troca de oleo")
                                .price(new BigDecimal("180.00")).build(),
                        ServiceOrderItem.builder().serviceId(2L).serviceName("Alinhamento")
                                .price(new BigDecimal("150.00")).build()
                ),
                NOW
        );
    }
}
