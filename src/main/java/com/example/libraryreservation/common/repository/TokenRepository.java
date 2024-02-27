package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.model.TokenModel;
import com.example.libraryreservation.common.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenModel, Long > {
    Optional<TokenModel> findTokenModelByUserModel(UserModel userModel);
}
