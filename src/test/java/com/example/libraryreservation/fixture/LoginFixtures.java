package com.example.libraryreservation.fixture;

import com.example.libraryreservation.auth.dto.LoginDto;

public class LoginFixtures {
    public static LoginDto loginAddressOne() {
        return new LoginDto("01099716733","1234567");
    }

    public static LoginDto loginAddressTwo() {
        return new LoginDto("01099716737","1234567");
    }
}
