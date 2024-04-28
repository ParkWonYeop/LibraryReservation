package com.example.libraryreservation.common.model;

import com.example.libraryreservation.common.enums.RoomEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Setter
@Getter
@Entity(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    @Column(name = "room_type", nullable = false)
    private RoomEnum roomType;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;
}
