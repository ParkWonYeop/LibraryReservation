package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.test.TestFilter;
import com.example.libraryreservation.common.test.TestSecurityConfig;
import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.enums.RoomEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationTestCase {
    @Autowired
    private ReservationController reservationController;

    @DisplayName("예약 테스트")
    @Test
    public void reservationAddTest() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomType(RoomEnum.STUDYING);
        reservationDto.setSeatNumber(1);
        reservationDto.setStartTime(startTime);
        reservationDto.setEndTime(startTime.plusHours(1));

        String result = reservationController.reservationSeat(reservationDto);

        assertEquals(result,"success");
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    public void reservationDeleteTest() {
        List<ReservationModel> reservationModelList = reservationController.getReservationList();

        String result = reservationController.deleteReservation(reservationModelList.get(0).getReservationId());

        assertEquals(result,"success");
    }
}
