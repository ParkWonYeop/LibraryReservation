package com.example.libraryreservation.room;

import com.example.libraryreservation.enums.RoomEnum;
import com.example.libraryreservation.model.RoomModel;
import com.example.libraryreservation.model.SeatModel;
import com.example.libraryreservation.repository.RoomRepository;
import com.example.libraryreservation.response.Message;
import com.example.libraryreservation.response.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.libraryreservation.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Message getRoomList() {
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("getRoomList Success");
        message.setData(roomRepository.findAll());
        return message;
    }

    public Message getRoom(RoomEnum roomType) {
        Message message = new Message();
        Optional<RoomModel> roomModelOptional = roomRepository.findRoomModelByRoomType(roomType);
        if(roomModelOptional.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("Not Found RoomType");
            return message;
        }
        message.setStatus(StatusEnum.OK);
        message.setMessage("getRoom Success");
        message.setData(roomModelOptional.get());
        return message;
    }
}
