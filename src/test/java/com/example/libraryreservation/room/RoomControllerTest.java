package com.example.libraryreservation.room;

import com.example.libraryreservation.auth.AuthController;
import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.common.model.TokenModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RoomControllerTest {
    @Autowired
    private AuthController authController;
    @Autowired
    private MockMvc mockMvc;
    protected MockHttpSession session;

    @Before
    public void setUp() {
        this.session = new MockHttpSession();
        LoginDto loginDto = new LoginDto("01099716733","1234567");
        TokenModel tokenModel = authController.login(loginDto);

        this.session.setAttribute("accessToken", tokenModel.getAccessToken());
    }

    @After
    public void cleanSession() {
        session.clearAttributes();
    }

    @DisplayName("방 리스트")
    @Test
    public void roomListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room/list")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.[0].roomId").value(1),
                        jsonPath("$.[0].roomType").value("DIGITAL"),
                        jsonPath("$.[0].seatNumber").value(1),
                        jsonPath("$.[29].roomId").value(30),
                        jsonPath("$.[29].roomType").value("STUDYING"),
                        jsonPath("$.[29].seatNumber").value(15)
                )
                .andDo(print());
    }

    @DisplayName("방 종류")
    @Test
    public void roomTypeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", "STUDYING")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.[0].roomId").value(16),
                        jsonPath("$.[0].roomType").value("STUDYING"),
                        jsonPath("$.[0].seatNumber").value(1),
                        jsonPath("$.[14].roomId").value(30),
                        jsonPath("$.[14].roomType").value("STUDYING"),
                        jsonPath("$.[14].seatNumber").value(15)
                )
                .andDo(print());
    }

    @DisplayName("방 종류 - 오타")
    @Test
    public void roomTypeErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", "STUDYINGs")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'com.example.libraryreservation.common.enums.RoomEnum'; Failed to convert from type [java.lang.String] to type [@org.springframework.web.bind.annotation.RequestParam com.example.libraryreservation.common.enums.RoomEnum] for value [STUDYINGs]"))
                .andDo(print());
    }

    @DisplayName("방 종류 - 공백")
    @Test
    public void roomTypeBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("방 종류 - 특수 문자")
    @Test
    public void roomTypeSpecialStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'com.example.libraryreservation.common.enums.RoomEnum'; Failed to convert from type [java.lang.String] to type [@org.springframework.web.bind.annotation.RequestParam com.example.libraryreservation.common.enums.RoomEnum] for value [*]"))
                .andDo(print());
    }
}
