package com.oficinaapp.oficina_app.application.dto.vehicle;

import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VehicleRequest(

        @NotNull VehicleType type,
        @NotBlank String brand,
        @NotBlank String model,
        @NotBlank String year,
        @NotBlank @Size(max = 10) String plate,

        /** Only for FIPE covered types; ignored otherwise. */
        String fipeCode,

        @DecimalMin("0.00") BigDecimal fipeValue

) {
}
