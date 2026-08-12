package com.oficinaapp.oficina_app.application.port;

import com.oficinaapp.oficina_app.domain.model.UserAccount;

public interface AccessTokenIssuer {

    AccessToken issueFor(UserAccount account);
}
