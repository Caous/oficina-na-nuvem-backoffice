package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.Cliente;
import com.oficinaapp.oficina_app.domain.repository.ClienteRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ClienteEntity;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.ClienteMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final JpaClienteRepository jpaClienteRepository;

    public ClienteRepositoryImpl(JpaClienteRepository jpaClienteRepository) {
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {

        ClienteEntity entity = ClienteMapper.toEntity(cliente);

        ClienteEntity entitySalva = jpaClienteRepository.save(entity);

        return ClienteMapper.toDomain(entitySalva);
    }
}