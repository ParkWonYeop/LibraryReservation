package com.example.libraryreservation.fixture;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.auth.dto.RefreshDto;
import com.example.libraryreservation.auth.dto.SignupDto;

public class AuthFixtures {
    public static LoginDto loginAddressOne() {
        return new LoginDto("01099716733", "1234567");
    }

    public static LoginDto loginAddressTwo() {
        return new LoginDto("01099716737", "1234567");
    }

    public static LoginDto loginAddressPhoneNumber(String phoneNumber) {
        return new LoginDto(phoneNumber, "1234567");
    }

    public static LoginDto loginAddressPassword(String password) {
        return new LoginDto("01099716737", password);
    }

    public static SignupDto signupAddress() {
        return new SignupDto("01099716734", "1234567", "박원엽");
    }

    public static SignupDto signupAddressPhoneNumber(String phoneNumber) {
        return new SignupDto(phoneNumber, "1234567", "박원엽");
    }

    public static SignupDto signupAddressPassword(String password) {
        return new SignupDto("01099716735", password, "박원엽");
    }

    public static SignupDto signupAddressName(String name) {
        return new SignupDto("01099716733", "12345673", name);
    }

    public static RefreshDto refreshToken(String refreshToken) {
        return new RefreshDto("01099716733", refreshToken);
    }
}
