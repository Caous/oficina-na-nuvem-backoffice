package com.oficinaapp.oficina_app.infrastructure.integration.fipe;

import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import com.oficinaapp.oficina_app.domain.exception.OperationNotAllowedException;

/**
 * FIPE splits its table in three; our vehicle types have to be mapped onto it.
 * Utilities are priced as cars there.
 */
final class FipeVehicleSegment {

    private FipeVehicleSegment() {
    }

    static String pathOf(VehicleType type) {
        if (!type.isCoveredByFipe()) {
            throw new OperationNotAllowedException("The FIPE table does not cover this kind of vehicle.");
        }

        return switch (type) {
            case CAR, UTILITY -> "carros";
            case MOTORCYCLE -> "motos";
            case TRUCK -> "caminhoes";
            default -> throw new OperationNotAllowedException("The FIPE table does not cover this kind of vehicle.");
        };
    }
}
