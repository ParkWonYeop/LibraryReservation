package com.example.libraryreservation.room;

import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.RoomEntity;
import com.example.libraryreservation.common.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<RoomEntity> getRoomList() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<RoomEntity> getRoom(RoomEnum roomType) {
        List<RoomEntity> roomEntityList = roomRepository.findRoomModelByRoomType(roomType);
        if (roomEntityList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ROOM_NOT_FOUND);
        }
        return roomEntityList;
    }
}
