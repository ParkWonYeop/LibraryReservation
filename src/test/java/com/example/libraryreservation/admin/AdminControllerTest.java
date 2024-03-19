package com.example.libraryreservation.admin;

import com.example.libraryreservation.auth.AuthController;
import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.common.model.ReservationModel;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private AdminController adminController;
    @Autowired
    private MockMvc mockMvc;
    protected MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        this.session = new MockHttpSession();
        LoginDto loginDto = new LoginDto("01099716733","1234567");
        TokenModel tokenModel = authController.login(loginDto);

        this.session.setAttribute("accessToken", tokenModel.getAccessToken());
    }

    @After
    public void clean() {
        session.clearAttributes();
    }

    @DisplayName("예약 리스트")
    @Test
    public void reservationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/reservation")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].['reservationId']").value(1))
                .andExpect(jsonPath("$[0].['seatNumber'].['roomId']").value(1))
                .andExpect(jsonPath("$[0].['seatNumber'].['roomType']").value("DIGITAL"))
                .andExpect(jsonPath("$[0].['seatNumber'].['seatNumber']").value(1))
                .andExpect(jsonPath("$[0].['startTime']").value("2024-06-04T00:00:00"))
                .andExpect(jsonPath("$[0].['endTime']").value("2024-06-04T01:00:00"))
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    public void deleteSuccessTest() throws Exception {
        List<ReservationModel> reservationModel = adminController.getReservationList();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 없는 인덱스")
    @Test
    public void deleteWrongIndexTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", "0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("예약을 찾을 수 없습니다."))
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 빈칸")
    @Test
    public void deleteBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"\""))
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    public void deleteSpecialCharacterTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Failed to convert value of type 'java.lang.String' to required type 'long'; For input string: \"*\""))
                .andDo(print());
    }
}
