package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.VehicleType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * A vehicle in a customer's garage. The FIPE fields are filled only for the
 * types that table covers; the others are typed in by the person.
 */
@Getter
@Builder(toBuilder = true)
public class Vehicle {

    private final Long id;

    /** Account of the customer who owns it. */
    private final Long ownerId;

    private final VehicleType type;
    private final String brand;
    private final String model;

    /** As FIPE labels it, e.g. "2020 Gasolina". */
    private final String year;

    private final String plate;

    /** FIPE model code, e.g. "014057-8"; empty outside the table. */
    private final String fipeCode;

    /** FIPE reference value; zero outside the table. */
    private final BigDecimal fipeValue;

    /** "Honda Civic 2.0 • ABC1D23", the line the app shows in lists. */
    public String shortDescription() {
        return "%s %s • %s".formatted(brand, model, plate);
    }
}
