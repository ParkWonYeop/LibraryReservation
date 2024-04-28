package com.example.libraryreservation.common.task;

import com.example.libraryreservation.common.model.ReservationEntity;
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
        List<ReservationEntity> reservationEntityList = reservationRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (ReservationEntity reservationEntity : reservationEntityList) {
            if (now.isAfter(reservationEntity.getEndTime())) {
                reservationRepository.delete(reservationEntity);
            }
        }

        log.info("ScheduledTaks : 삭제완료");
    }
}
