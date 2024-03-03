package com.example.libraryreservation.room;

import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.RoomModel;
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
    public List<RoomModel> getRoomList() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<RoomModel> getRoom(RoomEnum roomType) {
        List<RoomModel> roomModelList = roomRepository.findRoomModelByRoomType(roomType);
        if (roomModelList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ROOM_NOT_FOUND);
        }
        return roomModelList;
    }
}
