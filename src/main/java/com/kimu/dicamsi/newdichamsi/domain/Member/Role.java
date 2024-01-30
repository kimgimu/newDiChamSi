package com.kimu.dicamsi.newdichamsi.domain.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    SOCIAL("SOCIAL");
    private final String value;
}
