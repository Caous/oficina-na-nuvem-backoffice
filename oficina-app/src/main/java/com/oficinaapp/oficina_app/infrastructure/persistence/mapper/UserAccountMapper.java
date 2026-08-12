package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.UserAccount;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.UserAccountEntity;

public final class UserAccountMapper {

    private UserAccountMapper() {
    }

    public static UserAccountEntity toEntity(UserAccount account) {
        UserAccountEntity entity = new UserAccountEntity();

        entity.setId(account.getId());
        entity.setName(account.getName());
        entity.setEmail(account.getEmail());
        entity.setPasswordHash(account.getPasswordHash());
        entity.setPhone(account.getPhone());
        entity.setDocument(account.getDocument());
        entity.setRole(account.getRole());
        entity.setWorkshopId(account.getWorkshopId());
        entity.setJobTitle(account.getJobTitle());
        entity.setAddress(AddressMapper.toEmbeddable(account.getAddress()));
        entity.setEnabled(account.isEnabled());
        entity.setCreatedAt(account.getCreatedAt());
        entity.setUpdatedAt(account.getUpdatedAt());

        return entity;
    }

    public static UserAccount toDomain(UserAccountEntity entity) {
        return UserAccount.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .phone(entity.getPhone())
                .document(entity.getDocument())
                .role(entity.getRole())
                .workshopId(entity.getWorkshopId())
                .jobTitle(entity.getJobTitle())
                .address(AddressMapper.toDomain(entity.getAddress()))
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
