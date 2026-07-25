package com.oficinaapp.oficina_app.application.dto.funcionario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuncionarioResponse {

    private Long id;
    private String nome;
    private String email;

}
