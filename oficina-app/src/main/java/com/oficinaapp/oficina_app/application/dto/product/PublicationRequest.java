package com.oficinaapp.oficina_app.application.dto.product;

import jakarta.validation.constraints.NotNull;

public record PublicationRequest(@NotNull Boolean published) {
}
