package com.xuanluan.mc.sdk.authenticate.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CurrentUser {
    private String id;
    private String orgId;
    private String username;
    private String email;
    private Map<String, Map<String, Object>> userRoles;
}
