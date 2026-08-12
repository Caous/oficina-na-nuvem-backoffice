package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Address;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.AddressEmbeddable;

public final class AddressMapper {

    private AddressMapper() {
    }

    public static AddressEmbeddable toEmbeddable(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressEmbeddable(
                address.zipCode(),
                address.street(),
                address.number(),
                address.complement(),
                address.district(),
                address.city(),
                address.state()
        );
    }

    public static Address toDomain(AddressEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        return new Address(
                embeddable.getZipCode(),
                embeddable.getStreet(),
                embeddable.getNumber(),
                embeddable.getComplement(),
                embeddable.getDistrict(),
                embeddable.getCity(),
                embeddable.getState()
        );
    }
}
