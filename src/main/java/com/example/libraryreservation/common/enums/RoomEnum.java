package com.example.libraryreservation.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomEnum {
    DIGITAL("digital"),
    READING("reading"),
    STUDYING("studying");

    final String roomType;
}
