package com.oficinaapp.oficina_app.application.dto.fipe;

import java.math.BigDecimal;

public record FipeQuoteResponse(

        String brandName,
        String modelName,
        String yearLabel,
        String fipeCode,
        BigDecimal value

) {
}
