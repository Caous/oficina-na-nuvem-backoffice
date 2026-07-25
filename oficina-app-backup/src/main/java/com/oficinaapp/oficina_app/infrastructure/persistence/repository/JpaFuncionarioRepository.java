package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFuncionarioRepository extends JpaRepository<FuncionarioEntity, Long> {
}
