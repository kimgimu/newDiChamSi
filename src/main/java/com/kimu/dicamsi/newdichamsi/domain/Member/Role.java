package com.kimu.dicamsi.newdichamsi.domain.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    SOCIAL("ROLE_SOCIAL");
    private final String value;
}
