package org.xuanluan.mc.sdk.authenticate.service;

import org.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;

import java.util.function.Supplier;

public interface ICurrentUserService {
    CurrentUser get(String clientId, String token, Supplier<CurrentUser> userSupplier);
}
