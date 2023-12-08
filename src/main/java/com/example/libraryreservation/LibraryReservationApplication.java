package com.example.libraryreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LibraryReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryReservationApplication.class, args);
    }

}
