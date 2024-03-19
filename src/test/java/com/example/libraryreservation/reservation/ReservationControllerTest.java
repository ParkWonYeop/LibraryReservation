package com.example.libraryreservation.reservation;

import com.example.libraryreservation.admin.AdminController;
import com.example.libraryreservation.auth.AuthController;
import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.model.TokenModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ReservationControllerTest {
    @Autowired
    private AuthController authController;
    @Autowired
    private AdminController adminController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    protected MockHttpSession session;

    @Before
    public void setUp() {
        this.session = new MockHttpSession();
        LoginDto loginDto = new LoginDto("01099716733","1234567");
        TokenModel tokenModel = authController.login(loginDto);

        this.session.setAttribute("accessToken", tokenModel.getAccessToken());

        LoginDto loginDto2 = new LoginDto("01099716737","1234567");
        TokenModel tokenModel2 = authController.login(loginDto2);

        this.session.setAttribute("accessToken2", tokenModel2.getAccessToken());
    }

    @After
    public void cleanSession() {
        session.clearAttributes();
    }

    @DisplayName("예약 테스트 - 성공")
    @Test
    public void addSuccessTest() throws Exception {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYING");
        body.put("seatNumber", "1");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken2")))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("예약 테스트 - roomType")
    @Test
    public void addRoomTypeTest() throws Exception {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYINGs");
        body.put("seatNumber", "1");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("JSON parse error: Cannot deserialize value of type `com.example.libraryreservation.common.enums.RoomEnum` from String \"STUDYINGs\": not one of the values accepted for Enum class: [DIGITAL, STUDYING, READING]"))
                .andDo(print());
    }

    @DisplayName("예약 테스트 - seatNumber/문자")
    @Test
    public void addSeatNumberStringTest() throws Exception {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYING");
        body.put("seatNumber", "a");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("예약 테스트 - seatNumber/특수문자")
    @Test
    public void addSeatNumberSpecialStringTest() throws Exception {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYING");
        body.put("seatNumber", "*");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("예약 테스트 - seatNumber/공백")
    @Test
    public void addSeatBlankTest() throws Exception {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYING");
        body.put("seatNumber", " ");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("예약 테스트 - time/past")
    @Test
    public void addTimePastTest() throws Exception {
        Map<String, String> body = new HashMap<>();

        LocalDateTime startTime = LocalDateTime.now().plusHours(-2).withMinute(0).withSecond(0).withNano(0);

        body.put("roomType", "STUDYING");
        body.put("seatNumber", "1");
        body.put("startTime", startTime.toString());
        body.put("endTime", startTime.plusHours(1).toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    public void deleteTest() throws Exception {
        List<ReservationModel> reservationModel = adminController.getReservationList();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 문자")
    @Test
    public void deleteStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", "a")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"a\""))
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    public void deleteSpecialStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"*\""))
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 공백")
    @Test
    public void deleteBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"\""))
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 다른 유저")
    @Test
    public void deleteOtherUserTest() throws Exception {
        List<ReservationModel> reservationModel = adminController.getReservationList();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", String.valueOf(1))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("유저가 일치 하지 않습니다."))
                .andDo(print());
    }
}
