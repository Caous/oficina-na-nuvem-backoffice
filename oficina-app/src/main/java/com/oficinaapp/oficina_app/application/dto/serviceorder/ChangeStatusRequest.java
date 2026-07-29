package com.oficinaapp.oficina_app.application.dto.serviceorder;

import com.oficinaapp.oficina_app.domain.enums.ServiceOrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest(@NotNull ServiceOrderStatus status) {
}
