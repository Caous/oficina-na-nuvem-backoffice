package com.oficinaapp.oficina_app.domain.model;

public record Address(
        String zipCode,
        String street,
        String number,
        String complement,
        String district,
        String city,
        String state
) {
}
