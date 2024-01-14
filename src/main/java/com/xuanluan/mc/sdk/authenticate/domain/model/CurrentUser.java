package com.xuanluan.mc.sdk.authenticate.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CurrentUser {
    private String orgId;
    private String username;
    private Map<String, GrantedAuthority> userRoles;
}
