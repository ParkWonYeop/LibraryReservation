package com.example.libraryreservation.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PermissionEnum {
    USER("user"),
    ADMIN("admin");

    final String permission;
}
