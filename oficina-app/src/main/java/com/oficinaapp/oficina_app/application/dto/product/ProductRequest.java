package com.oficinaapp.oficina_app.application.dto.product;

import com.oficinaapp.oficina_app.domain.enums.ProductCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank String name,
        @Size(max = 500) String description,
        @NotNull ProductCategory category,
        @NotBlank @Size(max = 40) String sku,

        @NotNull @DecimalMin("0.01") @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @NotNull @Min(0) Integer stockQuantity,

        /** Optional on create; the product stays private when omitted. */
        Boolean published

) {

    public boolean publishedOrDefault(boolean current) {
        return published == null ? current : published;
    }
}
