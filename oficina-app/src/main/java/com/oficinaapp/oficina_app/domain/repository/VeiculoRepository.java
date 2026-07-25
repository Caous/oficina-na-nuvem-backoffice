package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.VeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {

}

