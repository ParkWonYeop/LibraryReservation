package com.example.libraryreservation.common.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Setter
@Getter
@Entity(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private RoomEntity seatNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
}
