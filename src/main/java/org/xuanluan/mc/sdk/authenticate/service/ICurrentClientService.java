package org.xuanluan.mc.sdk.authenticate.service;

import org.xuanluan.mc.sdk.authenticate.domain.model.CurrentClient;

import java.util.function.Supplier;

public interface ICurrentClientService {
    CurrentClient get(String id, Supplier<CurrentClient> clientSupplier);
}
