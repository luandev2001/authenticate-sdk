package org.xuanluan.mc.sdk.authenticate.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentClient {
    private String id;
    private String name;
    private boolean isActive;
}
