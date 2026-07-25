package com.oficinaapp.oficina_app.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    private String cpf;

    private LocalDate dataNascimento;

    private boolean ativo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}