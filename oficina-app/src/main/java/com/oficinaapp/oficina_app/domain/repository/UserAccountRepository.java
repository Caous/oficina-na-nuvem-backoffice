package com.oficinaapp.oficina_app.domain.repository;

import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository {

    UserAccount save(UserAccount account);

    Optional<UserAccount> findById(Long id);

    /** The e-mail must already be normalized by {@code TextNormalizer.email}. */
    Optional<UserAccount> findByEmail(String email);

    /** The document must already be reduced to digits by {@code TextNormalizer.digits}. */
    Optional<UserAccount> findByDocument(String document);

    List<UserAccount> findByWorkshopIdAndRole(Long workshopId, UserRole role);

    List<UserAccount> findAllById(List<Long> ids);

    boolean existsByEmail(String email);

    boolean existsByDocument(String document);
}
