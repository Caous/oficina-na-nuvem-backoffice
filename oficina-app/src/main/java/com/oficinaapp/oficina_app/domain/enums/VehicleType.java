package com.oficinaapp.oficina_app.domain.enums;

public enum VehicleType {

    CAR(true),
    MOTORCYCLE(true),
    TRUCK(true),
    UTILITY(true),
    JET_SKI(false),
    AIRCRAFT(false);

    private final boolean coveredByFipe;

    VehicleType(boolean coveredByFipe) {
        this.coveredByFipe = coveredByFipe;
    }

    /** Types outside the FIPE table are filled in by hand. */
    public boolean isCoveredByFipe() {
        return coveredByFipe;
    }
}
