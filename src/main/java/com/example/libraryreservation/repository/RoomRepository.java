package com.example.libraryreservation.repository;

import com.example.libraryreservation.enums.RoomEnum;
import com.example.libraryreservation.model.RoomModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<RoomModel, String > {
    Optional<RoomModel> findRoomModelByRoomType(RoomEnum roomType);
}
