package com.example.libraryreservation.room;

import com.example.libraryreservation.common.test.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(RoomController.class)
@Import(TestSecurityConfig.class)
public class RoomMvcTestCase {
    @MockBean
    private RoomService roomService;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("방 리스트 테스트")
    @Test
    public void roomListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/room/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("방 종류 테스트")
    @Test
    public void roomTypeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/room")
                        .param("roomType","DIGITAL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
