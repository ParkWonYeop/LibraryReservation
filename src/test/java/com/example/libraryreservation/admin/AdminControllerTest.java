package com.example.libraryreservation.admin;

import com.example.libraryreservation.auth.AuthController;
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
import org.springframework.transaction.annotation.Transactional;

import static com.example.libraryreservation.fixture.AuthFixtures.loginAddressOne;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AuthController authController;
    @Autowired
    private MockMvc mockMvc;
    protected MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession();

        TokenModel tokenModel = authController.login(loginAddressOne());

        session.setAttribute("accessToken", tokenModel.getAccessToken());
    }

    @After
    public void clean() {
        session.clearAttributes();
    }

    @DisplayName("예약 리스트")
    @Test
    public void reservationTest() throws Exception {
        mockMvc.perform(get("/admin/reservation")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").isArray(),
                        jsonPath("$[0].reservationId").value(1),
                        jsonPath("$[0].seatNumber.roomId").value(1),
                        jsonPath("$[0].seatNumber.roomType").value("DIGITAL"),
                        jsonPath("$[0].seatNumber.seatNumber").value(1),
                        jsonPath("$[0].startTime").value("2024-06-04T00:00:00"),
                        jsonPath("$[0].endTime").value("2024-06-04T01:00:00")
                );
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    public void deleteSuccessTest() throws Exception {
        mockMvc.perform(delete("/admin/reservation")
                        .param("id", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(status().isOk());
    }

    @DisplayName("예약 삭제 - 없는 인덱스")
    @Test
    public void deleteWrongIndexTest() throws Exception {
        mockMvc.perform(delete("/admin/reservation")
                        .param("id", "0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("예약을 찾을 수 없습니다.")
                );
    }

    @DisplayName("예약 삭제 - 빈칸")
    @Test
    public void deleteBlankTest() throws Exception {
        mockMvc.perform(delete("/admin/reservation")
                        .param("id", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    public void deleteSpecialCharacterTest() throws Exception {
        mockMvc.perform(delete("/admin/reservation")
                        .param("id", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("타입이 잘못되었습니다.")
                );
    }
}
