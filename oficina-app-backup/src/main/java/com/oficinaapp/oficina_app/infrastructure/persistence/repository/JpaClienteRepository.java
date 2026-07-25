package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
