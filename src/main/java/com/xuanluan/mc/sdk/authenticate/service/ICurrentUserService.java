package com.xuanluan.mc.sdk.authenticate.service;

import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;

import java.util.function.Supplier;

public interface ICurrentUserService {
    CurrentUser putIfAbsent(String clientId, String token, Supplier<CurrentUser> userSupplier);
}
