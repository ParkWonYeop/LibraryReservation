package com.example.libraryreservation.admin;

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

@WebMvcTest(AdminController.class)
@Import(TestSecurityConfig.class)
public class AdminMvcTestCase {
    @MockBean
    private AdminService adminService;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("예약 리스트 테스트")
    @Test
    public void adminReservationListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservation"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    public void adminReservationDeleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/reservation")
                        .param("id","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
