package com.example.libraryreservation.reservation;

import com.example.libraryreservation.dto.ReservationDto;
import com.example.libraryreservation.model.ReservationModel;
import com.example.libraryreservation.model.RoomModel;
import com.example.libraryreservation.model.UserModel;
import com.example.libraryreservation.repository.ReservationRepository;
import com.example.libraryreservation.repository.RoomRepository;
import com.example.libraryreservation.repository.UserRepository;
import com.example.libraryreservation.response.Message;
import com.example.libraryreservation.response.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.libraryreservation.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public Message reservationSeat(ReservationDto reservationDto) {
        Message message = new Message();
        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();
        log.info(String.valueOf(startTime));

        Optional<RoomModel> roomModelOptional = roomRepository.findRoomModelByRoomType(reservationDto.getRoomType());
        if (roomModelOptional.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("Room not found");
            return message;
        }
        if(!findSeatNumber(roomModelOptional.get().getSeatList(), reservationDto.getSeatNumber())) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("SeatNumber not found");
            return message;
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());
        if(userModelOptional.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("User not found");
            return message;
        }

        UserModel userModel = userModelOptional.get();
        RoomModel roomModel = roomModelOptional.get();

        List<ReservationModel> reservationList = reservationRepository.findReservationModelsByUserModel(userModel);

        if(!reservationList.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("Already reservation user");
            return message;
        }

        Optional<ReservationModel> checkReservation = reservationRepository.findReservationModelByRoomModelAndSeatNumberAndStartTime(roomModel, reservationDto.getSeatNumber(), reservationDto.getStartTime());

        if(checkReservation.isPresent()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("Already reservation seat");
            return message;
        }

        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setRoomModel(roomModel);
        reservationModel.setSeatNumber(reservationDto.getSeatNumber());
        reservationModel.setUserModel(userModel);
        reservationModel.setStartTime(startTime);
        reservationModel.setEndTime(endTime);

        reservationRepository.save(reservationModel);

        message.setStatus(StatusEnum.OK);
        message.setMessage("Success");

        return message;
    }

    public Message getReservationList() {
        Message message = new Message();
        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());
        if(userModelOptional.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("User not found");
            return message;
        }

        UserModel userModel = userModelOptional.get();

        List<ReservationModel> reservationModelList = reservationRepository.findReservationModelsByUserModel(userModel);

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

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if(userModelOptional.isEmpty()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("User is not found");
            return message;
        }

        if(!Objects.equals(reservationModel.get().getUserModel().getPhoneNumber(), userModelOptional.get().getPhoneNumber())) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("User is not correct");
            return message;
        }

        reservationRepository.delete(reservationModel.get());

        message.setStatus(StatusEnum.OK);
        message.setMessage("Success");
        return message;
    }

    private boolean findSeatNumber(List<Integer> seatList, Integer seatNumber) {
        for(Integer i : seatList) {
            if(Objects.equals(i, seatNumber)) {
                return true;
            }
        }
        return false;
    }
}
