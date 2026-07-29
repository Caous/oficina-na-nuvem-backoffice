package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByEmail(String email);

    Optional<UserAccountEntity> findByDocument(String document);

    List<UserAccountEntity> findByWorkshopIdAndRoleOrderByNameAsc(Long workshopId, UserRole role);

    boolean existsByEmail(String email);

    boolean existsByDocument(String document);
}
