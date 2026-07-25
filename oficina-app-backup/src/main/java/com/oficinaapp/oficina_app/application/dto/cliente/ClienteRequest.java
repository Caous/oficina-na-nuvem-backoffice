package com.oficinaapp.oficina_app.application.dto.cliente;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private LocalDate dataNascimento;

}