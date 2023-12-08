package com.example.libraryreservation.auth;

import com.example.libraryreservation.dto.LoginDto;
import com.example.libraryreservation.jwt.JwtUtil;
import com.example.libraryreservation.model.TokenModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.libraryreservation.model.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.libraryreservation.repository.UserRepository;
import com.example.libraryreservation.response.Message;
import com.example.libraryreservation.response.StatusEnum;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private Message login(LoginDto loginDto) {
        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(loginDto.getPhoneNumber());
        Message message = new Message();
        if(userModel.isEmpty()) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("Not Found User");
            return message;
        }

        UserModel user = userModel.get();

        if(!encoder.matches(loginDto.getPassword(), user.getPassword())) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("Password is not matched");
            return message;
        }

        String accessToken = JwtUtil.generateToken(user, (long) (1000*60*10));
        String refreshToken = JwtUtil.generateToken(user,(long) (1000*60*60*3));

        TokenModel tokenModel = new TokenModel(accessToken, refreshToken);
        user.setTokenModel(tokenModel);
        userRepository.save(user);

        message.setMessage("Login Success");
        message.setStatus(StatusEnum.OK);

        return message;
    }
}
