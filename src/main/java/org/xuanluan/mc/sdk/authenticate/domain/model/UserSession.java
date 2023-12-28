package org.xuanluan.mc.sdk.authenticate.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserSession {
    private String clientId;
    private String orgId;
    private String username;
    private Set<String> roles;
}
