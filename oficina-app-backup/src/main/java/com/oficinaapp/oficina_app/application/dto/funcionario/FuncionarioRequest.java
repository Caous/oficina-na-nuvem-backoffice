package com.oficinaapp.oficina_app.application.dto.funcionario;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioRequest {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private LocalDate dataNascimento;

}
