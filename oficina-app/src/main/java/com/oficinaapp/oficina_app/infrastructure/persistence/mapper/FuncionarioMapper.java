package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Funcionario;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.FuncionarioEntity;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public static FuncionarioEntity toEntity(Funcionario funcionario) {

        FuncionarioEntity entity = new FuncionarioEntity();

        entity.setId(funcionario.getId());
        entity.setNome(funcionario.getNome());
        entity.setEmail(funcionario.getEmail());
        entity.setSenha(funcionario.getSenha());
        entity.setTelefone(funcionario.getTelefone());
        entity.setCpf(funcionario.getCpf());
        entity.setDataNascimento(funcionario.getDataNascimento());
        entity.setCreatedAt(funcionario.getCreatedAt());
        entity.setUpdatedAt(funcionario.getUpdatedAt());
        entity.setAtivo(funcionario.isAtivo());

        return entity;
    }

    public static Funcionario toDomain(FuncionarioEntity entity) {

        Funcionario funcionario = new Funcionario();

        funcionario.setId(entity.getId());
        funcionario.setNome(entity.getNome());
        funcionario.setEmail(entity.getEmail());
        funcionario.setSenha(entity.getSenha());
        funcionario.setTelefone(entity.getTelefone());
        funcionario.setCpf(entity.getCpf());
        funcionario.setDataNascimento(entity.getDataNascimento());
        funcionario.setCreatedAt(entity.getCreatedAt());
        funcionario.setUpdatedAt(entity.getUpdatedAt());
        funcionario.setAtivo(entity.isAtivo());

        return funcionario;
    }

}
