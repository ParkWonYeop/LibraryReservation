package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findUserModelByPhoneNumber(String phoneNumber);
}
