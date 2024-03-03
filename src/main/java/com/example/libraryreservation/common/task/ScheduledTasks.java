package com.example.libraryreservation.common.task;

import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {
    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "0 1 9-22 * * ?")
    public void deleteReservation() {
        List<ReservationModel> reservationModelList = reservationRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (ReservationModel reservationModel : reservationModelList) {
            if (now.isAfter(reservationModel.getEndTime())) {
                reservationRepository.delete(reservationModel);
            }
        }

        log.info("ScheduledTaks : 삭제완료");
    }
}
