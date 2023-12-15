package com.example.libraryreservation;

import com.example.libraryreservation.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoAuditing
@EnableMongoRepositories
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LibraryReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryReservationApplication.class, args);
    }

}
