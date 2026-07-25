package com.oficinaapp.oficina_app.application.dto.cliente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponse {

    private Long id;
    private String nome;
    private String email;

}