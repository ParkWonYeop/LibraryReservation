package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.test.TestSecurityConfig;
import com.example.libraryreservation.room.RoomController;
import com.example.libraryreservation.room.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ReservationController.class)
@Import(TestSecurityConfig.class)
public class ReservationMvcTestCase {
    @MockBean
    private ReservationService reservationService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("예약 리스트 테스트")
    @Test
    public void reservationListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservation"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    public void reservationDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id","0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 테스트")
    @Test
    public void reservationTest() throws Exception {
        Map<String,String> body = new HashMap<>();
        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType","STUDYING");
        body.put("seatNumber","1");
        body.put("startTime",startTime.toString());
        body.put("endTime",startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}
