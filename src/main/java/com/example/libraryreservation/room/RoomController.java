package com.example.libraryreservation.room;

import com.example.libraryreservation.enums.RoomEnum;
import com.example.libraryreservation.response.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/list")
    public ResponseEntity<Message> getRoomList(HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = roomService.getRoomList();

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @GetMapping()
    public ResponseEntity<Message> getRoom(@RequestParam RoomEnum roomType, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = roomService.getRoom(roomType);

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
