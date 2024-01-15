package com.example.libraryreservation.admin;

import com.example.libraryreservation.enums.RoomEnum;
import com.example.libraryreservation.model.ReservationModel;
import com.example.libraryreservation.model.RoomModel;
import com.example.libraryreservation.repository.ReservationRepository;
import com.example.libraryreservation.repository.RoomRepository;
import com.example.libraryreservation.repository.UserRepository;
import com.example.libraryreservation.response.Message;
import com.example.libraryreservation.response.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    public Message getReservationList() {
        Message message = new Message();
        List<ReservationModel> reservationModelList = reservationRepository.findAll();
        message.setStatus(StatusEnum.OK);
        message.setMessage("Success");
        message.setData(reservationModelList);
        return message;
    }

    public Message deleteReservation(String reservationId) {
        Message message = new Message();
        Optional<ReservationModel> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if(reservationModel.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("Reservation is not found");
            return message;
        }
        reservationRepository.delete(reservationModel.get());
        message.setMessage("Success");
        message.setStatus(StatusEnum.OK);
        return message;
    }
}
