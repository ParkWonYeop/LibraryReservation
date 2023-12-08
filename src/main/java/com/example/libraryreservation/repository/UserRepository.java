package com.example.libraryreservation.repository;

import com.example.libraryreservation.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String > {
    Optional<UserModel> findUserModelByPhoneNumber(String phoneNumber);
}
