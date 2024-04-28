package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findRoomModelByRoomType(RoomEnum roomType);
}
