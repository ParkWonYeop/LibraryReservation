package com.example.libraryreservation.model;

import com.example.libraryreservation.enums.RoomEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection="room")
public class RoomModel {
    @Id
    private String roomId;

    @Indexed(unique = true)
    @Field("room_type")
    private RoomEnum roomType;

    @Field("seat_list")
    private List<Integer> seatList = new ArrayList<>();

    public RoomModel(RoomEnum roomType) {
        this.roomType = roomType;
    }
}
