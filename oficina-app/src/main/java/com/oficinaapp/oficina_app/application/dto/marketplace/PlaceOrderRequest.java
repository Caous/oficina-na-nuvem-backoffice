package com.oficinaapp.oficina_app.application.dto.marketplace;

import com.oficinaapp.oficina_app.application.dto.common.AddressPayload;
import com.oficinaapp.oficina_app.domain.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PlaceOrderRequest(

        @NotEmpty @Valid List<OrderItemPayload> items,
        @NotNull PaymentMethod paymentMethod,
        @NotNull @Valid AddressPayload deliveryAddress

) {
}
