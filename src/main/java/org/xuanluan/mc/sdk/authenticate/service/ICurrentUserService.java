package org.xuanluan.mc.sdk.authenticate.service;

import org.xuanluan.mc.sdk.authenticate.domain.model.CurrentUserSession;

import java.util.function.Supplier;

public interface ICurrentUserService {
    CurrentUserSession get(String clientId, String token, Supplier<CurrentUserSession> userSupplier);
}
