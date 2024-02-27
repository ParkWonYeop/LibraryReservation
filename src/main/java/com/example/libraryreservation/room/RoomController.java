package com.example.libraryreservation.room;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.RoomModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Transactional(readOnly = true)
    @GetMapping("/list")
    public List<RoomModel> getRoomList() {
        return roomService.getRoomList();
    }

    @Transactional(readOnly = true)
    @GetMapping()
    public List<RoomModel> getRoom(@RequestParam(name = "roomType") RoomEnum roomType) {
        return roomService.getRoom(roomType);
    }
}
