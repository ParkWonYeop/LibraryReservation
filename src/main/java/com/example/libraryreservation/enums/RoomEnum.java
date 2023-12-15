package com.example.libraryreservation.enums;

public enum RoomEnum {
    DIGITAL("digital"),
    READING("reading"),
    STUDYING("studying");

    final String roomType;

    RoomEnum(String roomType) {
        this.roomType = roomType;
    }
}
