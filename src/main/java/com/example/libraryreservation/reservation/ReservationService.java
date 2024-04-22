package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.model.RoomModel;
import com.example.libraryreservation.common.model.UserModel;
import com.example.libraryreservation.common.repository.ReservationRepository;
import com.example.libraryreservation.common.repository.RoomRepository;
import com.example.libraryreservation.common.repository.UserRepository;
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
    public void reservationSeat(ReservationDto reservationDto) {
        LocalDateTime startTime = reservationDto.startTime();
        LocalDateTime endTime = reservationDto.endTime();

        RoomEnum roomEnum = RoomEnum.valueOf(reservationDto.roomType());

        List<RoomModel> roomModelList = roomRepository.findRoomModelByRoomType(roomEnum);
        if (roomModelList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ROOM_NOT_FOUND);
        }

        RoomModel roomModel = findSeatNumber(roomModelList, reservationDto.seatNumber());

        if (roomModel == null) {
            throw new LibraryReservationException(CommunalResponse.SEAT_NUMBER_NOT_FOUND);
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }

        UserModel userModel = userModelOptional.get();

        List<ReservationModel> reservationList = reservationRepository.findReservationModelsByUserModel(userModel);

        if (!reservationList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ALREADY_RESERVATION_USER);
        }

        Optional<ReservationModel> checkReservation = reservationRepository.findReservationModelBySeatNumberAndStartTime(
                roomModel, reservationDto.startTime()
        );

        if (checkReservation.isPresent()) {
            throw new LibraryReservationException(CommunalResponse.ALREADY_RESERVATION_SEAT);
        }

        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setSeatNumber(roomModel);
        reservationModel.setUserModel(userModel);
        reservationModel.setStartTime(startTime);
        reservationModel.setEndTime(endTime);

        reservationRepository.save(reservationModel);
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
    public void deleteReservation(long reservationId) {
        Optional<ReservationModel> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if (reservationModel.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.RESERVATION_NOT_FOUND);
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }

        if (!Objects.equals(reservationModel.get().getUserModel().getPhoneNumber(), userModelOptional.get().getPhoneNumber())) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_CORRECT);
        }

        reservationRepository.delete(reservationModel.get());
    }

    private RoomModel findSeatNumber(List<RoomModel> seatList, Integer seatNumber) {
        for (RoomModel roomModel : seatList) {
            if (Objects.equals(roomModel.getSeatNumber(), seatNumber)) {
                return roomModel;
            }
        }
        return null;
    }
}
