package com.oficinaapp.oficina_app.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo {

    private Long id;

    private String placa;

    private String marca;

    private String modelo;

    private Integer ano;

    private String cor;

    private Cliente cliente;

}
