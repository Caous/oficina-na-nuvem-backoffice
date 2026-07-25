package com.oficinaapp.oficina_app.application.dto.veiculo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoRequest {

    private String placa;

    private String marca;

    private String modelo;

    private Integer ano;

    private String cor;

    private Long clienteId;

}