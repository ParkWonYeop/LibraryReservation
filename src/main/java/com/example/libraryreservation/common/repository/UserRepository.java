package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserModelByPhoneNumber(String phoneNumber);
}
