package com.example.libraryreservation.room;

import com.example.libraryreservation.auth.AuthController;
import com.example.libraryreservation.common.model.TokenEntity;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.libraryreservation.fixture.AuthFixtures.loginAddressOne;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
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
        session = new MockHttpSession();
        TokenEntity tokenEntity = authController.login(loginAddressOne());
        session.setAttribute("accessToken", tokenEntity.getAccessToken());
    }

    @After
    public void cleanSession() {
        session.clearAttributes();
    }

    @DisplayName("방 리스트")
    @Test
    public void roomListTest() throws Exception {
        mockMvc.perform(get("/room/list")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").isArray(),
                        jsonPath("$.[0].roomId").value(1),
                        jsonPath("$.[0].roomType").value("DIGITAL"),
                        jsonPath("$.[0].seatNumber").value(1),
                        jsonPath("$.[18].roomId").value(19),
                        jsonPath("$.[18].roomType").value("STUDYING"),
                        jsonPath("$.[18].seatNumber").value(8)
                );
    }

    @DisplayName("방 종류")
    @Test
    public void roomTypeTest() throws Exception {
        mockMvc.perform(get("/room")
                        .param("roomType", "STUDYING")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").isArray(),
                        jsonPath("$.[0].roomId").value(12),
                        jsonPath("$.[0].roomType").value("STUDYING"),
                        jsonPath("$.[0].seatNumber").value(1),
                        jsonPath("$.[7].roomId").value(19),
                        jsonPath("$.[7].roomType").value("STUDYING"),
                        jsonPath("$.[7].seatNumber").value(8)
                );
    }

    @DisplayName("방 종류 - 오타")
    @Test
    public void roomTypeErrorTest() throws Exception {
        mockMvc.perform(get("/room")
                        .param("roomType", "STUDYINGs")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("Enum에 없는 값입니다.")
                );
    }

    @DisplayName("방 종류 - 공백")
    @Test
    public void roomTypeBlankTest() throws Exception {
        mockMvc.perform(get("/room")
                        .param("roomType", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(status().isBadRequest());
    }

    @DisplayName("방 종류 - 특수 문자")
    @Test
    public void roomTypeSpecialStringTest() throws Exception {
        mockMvc.perform(get("/room")
                        .param("roomType", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("Enum에 없는 값입니다.")
                );
    }
}
