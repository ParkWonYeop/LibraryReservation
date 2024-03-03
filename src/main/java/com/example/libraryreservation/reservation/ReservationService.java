package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.model.RoomModel;
import com.example.libraryreservation.common.model.UserModel;
import com.example.libraryreservation.common.repository.ReservationRepository;
import com.example.libraryreservation.common.repository.RoomRepository;
import com.example.libraryreservation.common.repository.UserRepository;
import com.example.libraryreservation.reservation.constant.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.libraryreservation.common.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public String reservationSeat(ReservationDto reservationDto) {
        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();

        List<RoomModel> roomModelList = roomRepository.findRoomModelByRoomType(reservationDto.getRoomType());
        if (roomModelList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ROOM_NOT_FOUND);
        }

        RoomModel roomModel = findSeatNumber(roomModelList, reservationDto.getSeatNumber());

        if (roomModel == null) {
            throw new LibraryReservationException(ReservationResponse.SEAT_NUMBER_NOT_FOUND);
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }

        UserModel userModel = userModelOptional.get();

        List<ReservationModel> reservationList = reservationRepository.findReservationModelsByUserModel(userModel);

        if (!reservationList.isEmpty()) {
            throw new LibraryReservationException(ReservationResponse.ALREADY_RESERVATION_USER);
        }

        Optional<ReservationModel> checkReservation = reservationRepository.findReservationModelBySeatNumberAndStartTime(
                roomModel, reservationDto.getStartTime()
        );

        if (checkReservation.isPresent()) {
            throw new LibraryReservationException(ReservationResponse.ALREADY_RESERVATION_SEAT);
        }

        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setSeatNumber(roomModel);
        reservationModel.setUserModel(userModel);
        reservationModel.setStartTime(startTime);
        reservationModel.setEndTime(endTime);

        reservationRepository.save(reservationModel);

        return "success";
    }

    @Transactional(readOnly = true)
    public List<ReservationModel> getReservationList() {
        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());
        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }
        return reservationRepository.findReservationModelsByUserModel(userModelOptional.get());
    }

    @Transactional
    public String deleteReservation(long reservationId) {
        Optional<ReservationModel> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if (reservationModel.isEmpty()) {
            throw new LibraryReservationException(ReservationResponse.RESERVATION_NOT_FOUND);
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }

        if (!Objects.equals(reservationModel.get().getUserModel().getPhoneNumber(), userModelOptional.get().getPhoneNumber())) {
            throw new LibraryReservationException(ReservationResponse.USER_NOT_CORRECT);
        }

        reservationRepository.delete(reservationModel.get());

        return "success";
    }

    private RoomModel findSeatNumber(List<RoomModel> seatList, Integer seatNumber) {
        for (RoomModel i : seatList) {
            if (Objects.equals(i.getSeatNumber(), seatNumber)) {
                return i;
            }
        }
        return null;
    }
}
