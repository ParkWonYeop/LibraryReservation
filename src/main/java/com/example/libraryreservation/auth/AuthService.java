package com.example.libraryreservation.auth;

import com.example.libraryreservation.dto.LoginDto;
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

    }
}
