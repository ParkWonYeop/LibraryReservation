package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.model.ReservationModel;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTestCase {
    @Autowired
    private AdminController adminController;

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
