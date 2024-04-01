package com.example.libraryreservation.reservation;

import com.example.libraryreservation.admin.AdminController;
import com.example.libraryreservation.auth.AuthController;
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

import static com.example.libraryreservation.fixture.LoginFixtures.loginAddressOne;
import static com.example.libraryreservation.fixture.LoginFixtures.loginAddressTwo;
import static com.example.libraryreservation.fixture.ReservationFixtures.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ReservationControllerTest {
    @Autowired
    private AuthController authController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession();
        TokenModel tokenModel = authController.login(loginAddressOne());
        session.setAttribute("accessToken", tokenModel.getAccessToken());

        TokenModel tokenModel2 = authController.login(loginAddressTwo());
        session.setAttribute("accessToken2", tokenModel2.getAccessToken());
    }

    @After
    public void cleanSession() {
        session.clearAttributes();
    }

    @DisplayName("예약 테스트 - 성공")
    @Test
    public void addSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(createReservationSeatNumber("1")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken2")))
                .andExpect(status().isCreated());
    }

    @DisplayName("예약 테스트 - roomType")
    @Test
    public void addRoomTypeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(createReservationRoomType("STUDYINGs")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 테스트 - seatNumber/문자")
    @Test
    public void addSeatNumberStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(createReservationSeatNumber("a")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 테스트 - seatNumber/특수문자")
    @Test
    public void addSeatNumberSpecialStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(createReservationSeatNumber("*")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 테스트 - seatNumber/공백")
    @Test
    public void addSeatBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(createReservationSeatNumber(" ")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("예약 테스트 - time/past")
    @Test
    public void addTimePastTest() throws Exception {
        LocalDateTime startTime = LocalDateTime.now().plusHours(-2).withMinute(0).withSecond(0).withNano(0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservation")
                        .content(objectMapper.writeValueAsString(createReservationDate(startTime)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("현재보다 과거입니다.")
                );
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isOk());
    }

    @DisplayName("예약 삭제 - 문자")
    @Test
    public void deleteStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", "a")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    public void deleteSpecialStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 삭제 - 공백")
    @Test
    public void deleteBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 삭제 - 다른 유저")
    @Test
    public void deleteOtherUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reservation")
                        .param("id", String.valueOf(1))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken2")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("유저가 일치 하지 않습니다.")
                );
    }
}
