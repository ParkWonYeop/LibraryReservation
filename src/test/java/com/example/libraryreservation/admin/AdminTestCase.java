package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.model.ReservationModel;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AdminTestCase {
    @Autowired
    private AdminController adminController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void success(){
        // mock session 을 reqeust에 실어서 보내준다.

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post()
                                .session(여기에)
                )
                .andExpectAll()
                .andDo()

    }

    @DisplayName("예약 리스트 테스트")
    @Test
    public void adminReservationTest() {
        List<ReservationModel> reservationModelList = adminController.getReservationList();

        assertFalse(reservationModelList.isEmpty());
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    public void adminReservationDeleteTest() {
        List<ReservationModel> reservationModelList = adminController.getReservationList();
        String result = adminController.deleteReservation(reservationModelList.get(0).getReservationId());

        assertEquals(result,"success");
    }
}
