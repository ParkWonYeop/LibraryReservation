package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.ReservationEntity;
import com.example.libraryreservation.common.model.RoomEntity;
import com.example.libraryreservation.common.model.UserEntity;
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

        List<RoomEntity> roomEntityList = roomRepository.findRoomModelByRoomType(roomEnum);
        if (roomEntityList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ROOM_NOT_FOUND);
        }

        RoomEntity roomEntity = findSeatNumber(roomEntityList, reservationDto.seatNumber());

        if (roomEntity == null) {
            throw new LibraryReservationException(CommunalResponse.SEAT_NUMBER_NOT_FOUND);
        }

        Optional<UserEntity> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = userModelOptional.get();

        List<ReservationEntity> reservationList = reservationRepository.findReservationModelsByUserModel(userEntity);

        if (!reservationList.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.ALREADY_RESERVATION_USER);
        }

        Optional<ReservationEntity> checkReservation = reservationRepository.findReservationModelBySeatNumberAndStartTime(
                roomEntity, reservationDto.startTime()
        );

        if (checkReservation.isPresent()) {
            throw new LibraryReservationException(CommunalResponse.ALREADY_RESERVATION_SEAT);
        }

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setSeatNumber(roomEntity);
        reservationEntity.setUserEntity(userEntity);
        reservationEntity.setStartTime(startTime);
        reservationEntity.setEndTime(endTime);

        reservationRepository.save(reservationEntity);
    }

    @Transactional(readOnly = true)
    public List<ReservationEntity> getReservationList() {
        Optional<UserEntity> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());
        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }
        return reservationRepository.findReservationModelsByUserModel(userModelOptional.get());
    }

    @Transactional
    public void deleteReservation(long reservationId) {
        Optional<ReservationEntity> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if (reservationModel.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.RESERVATION_NOT_FOUND);
        }

        Optional<UserEntity> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if (userModelOptional.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_FOUND);
        }

        if (!Objects.equals(reservationModel.get().getUserEntity().getPhoneNumber(), userModelOptional.get().getPhoneNumber())) {
            throw new LibraryReservationException(CommunalResponse.USER_NOT_CORRECT);
        }

        reservationRepository.delete(reservationModel.get());
    }

    private RoomEntity findSeatNumber(List<RoomEntity> seatList, Integer seatNumber) {
        for (RoomEntity roomEntity : seatList) {
            if (Objects.equals(roomEntity.getSeatNumber(), seatNumber)) {
                return roomEntity;
            }
        }
        return null;
    }
}
