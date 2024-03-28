package com.example.libraryreservation.fixture;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReservationFixtures {
    public static Map<String, String> createReservationSeatNumber(String seatNumber) {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYING");
        body.put("seatNumber", seatNumber);
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        return body;
    }

    public static Map<String, String> createReservationRoomType(String roomType) {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", roomType);
        body.put("seatNumber", "1");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        return body;
    }

    public static Map<String, String> createReservationDate(LocalDateTime startTime) {
        Map<String, String> body = new HashMap<>();

        body.put("roomType", "STUDYING");
        body.put("seatNumber", "1");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        return body;
    }
}
