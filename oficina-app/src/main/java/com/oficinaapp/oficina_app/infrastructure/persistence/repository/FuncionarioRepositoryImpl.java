package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.model.Funcionario;
import com.oficinaapp.oficina_app.domain.repository.FuncionariosRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.FuncionarioEntity;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.FuncionarioMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FuncionarioRepositoryImpl implements FuncionariosRepository{

    private final JpaFuncionarioRepository jpaFuncionarioRepository;

    public FuncionarioRepositoryImpl(JpaFuncionarioRepository jpaFuncionarioRepository){
        this.jpaFuncionarioRepository = jpaFuncionarioRepository;
    }

    @Override
    public Funcionario salvar (Funcionario funcionario){

        FuncionarioEntity entity = FuncionarioMapper.toEntity(funcionario);

        FuncionarioEntity entitySalva = jpaFuncionarioRepository.save(entity);

        return FuncionarioMapper.toDomain(entitySalva);

    }

}
