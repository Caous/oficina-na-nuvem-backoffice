package com.oficinaapp.oficina_app.application.dto.serviceorder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OpenServiceOrderRequest(

        @NotNull Long customerId,
        @NotNull Long vehicleId,
        @NotEmpty List<Long> serviceIds,

        /** Optional: an order may be opened before a mechanic is chosen. */
        Long assignedEmployeeId

) {
}
