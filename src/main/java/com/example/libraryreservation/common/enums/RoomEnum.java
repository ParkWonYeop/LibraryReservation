package com.example.libraryreservation.common.enums;

public enum RoomEnum {
    DIGITAL("digital"),
    READING("reading"),
    STUDYING("studying");

    final String roomType;

    RoomEnum(String roomType) {
        this.roomType = roomType;
    }
}
