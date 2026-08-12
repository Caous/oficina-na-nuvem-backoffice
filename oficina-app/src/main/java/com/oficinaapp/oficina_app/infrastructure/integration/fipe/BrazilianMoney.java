package com.oficinaapp.oficina_app.infrastructure.integration.fipe;

import java.math.BigDecimal;

/**
 * Turns "R$ 100.000,00" into a number. FIPE answers money as display text, so
 * somebody has to undo the formatting.
 */
final class BrazilianMoney {

    private BrazilianMoney() {
    }

    static BigDecimal parse(String formatted) {
        if (formatted == null || formatted.isBlank()) {
            return BigDecimal.ZERO;
        }

        String digitsOnly = formatted
                .replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();

        try {
            return new BigDecimal(digitsOnly);
        } catch (NumberFormatException exception) {
            return BigDecimal.ZERO;
        }
    }
}
