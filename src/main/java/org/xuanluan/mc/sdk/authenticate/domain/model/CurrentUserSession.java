package org.xuanluan.mc.sdk.authenticate.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class CurrentUserSession {
    private String orgId;
    private String username;
    private Collection<GrantedAuthority> authorities;
    private boolean isActive;
}
