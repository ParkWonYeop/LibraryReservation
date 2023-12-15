package com.example.libraryreservation.room;

import com.example.libraryreservation.enums.RoomEnum;
import com.example.libraryreservation.response.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/list")
    public Message getRoomList() {
        return  this.roomService.getRoomList();
    }

    @GetMapping()
    public Message getRoom(@RequestParam RoomEnum roomType) {
        return  this.roomService.getRoom(roomType);
    }
}
