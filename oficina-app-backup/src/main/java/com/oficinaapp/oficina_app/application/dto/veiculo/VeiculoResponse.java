package com.oficinaapp.oficina_app.application.dto.veiculo;

import lombok.Builder;

@Builder
public record VeiculoResponse(

        Long id,

        String placa,

        String marca,

        String modelo,

        Integer ano,

        String cor,

        Long clienteId

) {
}