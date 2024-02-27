package com.example.libraryreservation.common.enums;

public enum PermissionEnum {
    USER("user"),
    ADMIN("admin");

    final String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }
}
