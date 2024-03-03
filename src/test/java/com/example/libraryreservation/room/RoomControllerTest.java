package com.example.libraryreservation.room;

import com.example.libraryreservation.auth.AuthController;
import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.common.model.TokenModel;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
        LoginDto loginDto = new LoginDto();
        loginDto.setPhoneNumber("01099716733");
        loginDto.setPassword("1234567");
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("방 종류")
    @Test
    public void roomTypeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", "STUDYING")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("방 종류 - 오타")
    @Test
    public void roomTypeErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", "STUDYINGs")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("방 종류 - 공백")
    @Test
    public void roomTypeBlankTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", " ")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("방 종류 - 특수 문자")
    @Test
    public void roomTypeSpecialStringTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType", "*")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.session.getAttribute("accessToken")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }
}
