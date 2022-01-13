package com.example.infra42.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    STUDENT("ROLE_GUEST", "학생"),
    ADMIN("ROLE_ADMIN", "멘토");
    private final String key;
    private final String title;
}
