package com.xuanluan.mc.sdk.authenticate.service;

import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;

import java.util.function.Supplier;

public interface ICurrentUserService {
    CurrentUser get(String clientId, String token);

    CurrentUser putIfAbsent(String clientId, String token, Supplier<CurrentUser> userSupplier);
}
