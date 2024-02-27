package com.example.libraryreservation.auth;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.common.model.TokenModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTestCase {
    @Autowired
    AuthController authController;

    @DisplayName("로그인 테스트")
    @Test
    public void loginTest() {
        LoginDto loginDto = new LoginDto();
        loginDto.setPhoneNumber("01099716733");
        loginDto.setPassword("1234567");

        TokenModel result = authController.login(loginDto);

        assertFalse(result.getAccessToken().isEmpty());
    }
}
