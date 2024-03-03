package com.example.libraryreservation.admin;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    @Autowired
    private ObjectMapper objectMapper;
    protected MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        this.session = new MockHttpSession();
        LoginDto loginDto = new LoginDto();
        loginDto.setPhoneNumber("01099716733");
        loginDto.setPassword("1234567");
        TokenModel tokenModel = authController.login(loginDto);

        this.session.setAttribute("accessToken", tokenModel.getAccessToken());

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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andDo(print());
    }

    @After
    public void clean() {
        session.clearAttributes();
        List<ReservationModel> reservationModelList = adminController.getReservationList();
        for (ReservationModel reservationModel : reservationModelList) {
            adminController.deleteReservation(reservationModel.getReservationId());
        }
    }

    @DisplayName("예약 리스트")
    @Test
    public void reservationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/reservation")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 성공")
    @Test
    public void deleteSuccessTest() throws Exception {
        List<ReservationModel> reservationModel = adminController.getReservationList();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", String.valueOf(reservationModel.get(0).getReservationId()))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 없는 인덱스")
    @Test
    public void deleteWrongIndexTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", "0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 빈칸")
    @Test
    public void deleteBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("예약 삭제 - 특수 문자")
    @Test
    public void deleteSpecialCharacterTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reservation")
                        .param("id", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }
}
