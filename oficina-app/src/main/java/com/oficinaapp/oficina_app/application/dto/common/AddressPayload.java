package com.oficinaapp.oficina_app.application.dto.common;

import com.oficinaapp.oficina_app.domain.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Address as it travels in and out of the API. Shared by every module so the
 * app only ever learns one address shape.
 */
public record AddressPayload(

        @NotBlank String zipCode,
        @NotBlank String street,
        @NotBlank String number,
        String complement,
        @NotBlank String district,
        @NotBlank String city,
        @NotBlank @Size(min = 2, max = 2) String state

) {

    public Address toDomain() {
        return new Address(zipCode, street, number, complement, district, city, state);
    }

    public static AddressPayload from(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressPayload(
                address.zipCode(),
                address.street(),
                address.number(),
                address.complement(),
                address.district(),
                address.city(),
                address.state()
        );
    }
}
