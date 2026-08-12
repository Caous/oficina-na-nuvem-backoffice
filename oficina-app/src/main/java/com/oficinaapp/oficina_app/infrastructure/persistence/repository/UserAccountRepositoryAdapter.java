package com.oficinaapp.oficina_app.infrastructure.persistence.repository;

import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.domain.repository.UserAccountRepository;
import com.oficinaapp.oficina_app.infrastructure.persistence.mapper.UserAccountMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountRepositoryAdapter implements UserAccountRepository {

    private final JpaUserAccountRepository jpaRepository;

    public UserAccountRepositoryAdapter(JpaUserAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserAccount save(UserAccount account) {
        return UserAccountMapper.toDomain(jpaRepository.save(UserAccountMapper.toEntity(account)));
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return jpaRepository.findById(id).map(UserAccountMapper::toDomain);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserAccountMapper::toDomain);
    }

    @Override
    public Optional<UserAccount> findByDocument(String document) {
        return jpaRepository.findByDocument(document).map(UserAccountMapper::toDomain);
    }

    @Override
    public List<UserAccount> findByWorkshopIdAndRole(Long workshopId, UserRole role) {
        return jpaRepository.findByWorkshopIdAndRoleOrderByNameAsc(workshopId, role).stream()
                .map(UserAccountMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserAccount> findAllById(List<Long> ids) {
        return jpaRepository.findAllById(ids).stream()
                .map(UserAccountMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDocument(String document) {
        return jpaRepository.existsByDocument(document);
    }
}
