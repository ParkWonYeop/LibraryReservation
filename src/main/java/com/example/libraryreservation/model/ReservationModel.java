package com.example.libraryreservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Document(collection="reservation")
public class ReservationModel {
    @Id
    private String reservationId;

    @DBRef
    @Field("room_id")
    private RoomModel roomModel;

    @Field("seat_number")
    private Integer seatNumber;

    @DBRef
    @Field("user_id")
    private UserModel userModel;

    @Field("start_time")
    private LocalDateTime startTime;
    @Field("end_time")
    private LocalDateTime endTime;
}
