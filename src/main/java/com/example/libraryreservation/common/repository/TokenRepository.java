package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.model.TokenEntity;
import com.example.libraryreservation.common.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findTokenModelByUserModel(UserEntity userEntity);
}
