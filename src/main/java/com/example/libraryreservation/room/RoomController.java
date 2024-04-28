package com.example.libraryreservation.room;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.RoomEntity;
import com.example.libraryreservation.common.validation.ValidationSequence;
import com.example.libraryreservation.room.dto.RoomTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/list")
    public List<RoomEntity> getRoomList() {
        return roomService.getRoomList();
    }

    @GetMapping()
    public List<RoomEntity> getRoom(@Validated(ValidationSequence.class) RoomTypeDto roomTypeDto) {
        return roomService.getRoom(RoomEnum.valueOf(roomTypeDto.roomType()));
    }
}
