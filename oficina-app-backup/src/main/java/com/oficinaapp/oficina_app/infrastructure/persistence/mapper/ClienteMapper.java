package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Cliente;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public static ClienteEntity toEntity(Cliente cliente) {

        ClienteEntity entity = new ClienteEntity();

        entity.setId(cliente.getId());
        entity.setNome(cliente.getNome());
        entity.setEmail(cliente.getEmail());
        entity.setSenha(cliente.getSenha());
        entity.setTelefone(cliente.getTelefone());
        entity.setCpf(cliente.getCpf());
        entity.setDataNascimento(cliente.getDataNascimento());
        entity.setCreatedAt(cliente.getCreatedAt());
        entity.setUpdatedAt(cliente.getUpdatedAt());
        entity.setAtivo(cliente.isAtivo());

        return entity;
    }

    public static Cliente toDomain(ClienteEntity entity) {

        Cliente cliente = new Cliente();

        cliente.setId(entity.getId());
        cliente.setNome(entity.getNome());
        cliente.setEmail(entity.getEmail());
        cliente.setSenha(entity.getSenha());
        cliente.setTelefone(entity.getTelefone());
        cliente.setCpf(entity.getCpf());
        cliente.setDataNascimento(entity.getDataNascimento());
        cliente.setCreatedAt(entity.getCreatedAt());
        cliente.setUpdatedAt(entity.getUpdatedAt());
        cliente.setAtivo(entity.getAtivo());

        return cliente;
    }
}