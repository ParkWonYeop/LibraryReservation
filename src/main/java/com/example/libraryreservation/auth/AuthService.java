package com.example.libraryreservation.auth;

import com.example.libraryreservation.dto.LoginDto;
import com.example.libraryreservation.dto.RefreshDto;
import com.example.libraryreservation.dto.SignupDto;
import com.example.libraryreservation.jwt.JwtUtil;
import com.example.libraryreservation.model.TokenModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.libraryreservation.model.UserModel;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.libraryreservation.repository.UserRepository;
import com.example.libraryreservation.response.Message;
import com.example.libraryreservation.response.StatusEnum;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public Message login(LoginDto loginDto) {
        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(loginDto.getPhoneNumber());
        Message message = new Message();
        if(userModel.isEmpty()) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("Not Found User");
            log.error("login : Not Found User");
            return message;
        }

        UserModel user = userModel.get();

        if(!encoder.matches(loginDto.getPassword(), user.getPassword())) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("Password is not matched");
            log.error("login : Password is not matched");
            return message;
        }

        String accessToken = JwtUtil.generateToken(user);
        String refreshToken = JwtUtil.createRefreshToken();

        TokenModel tokenModel = new TokenModel(accessToken, refreshToken);
        user.setTokenModel(tokenModel);
        userRepository.save(user);

        message.setMessage("Login Success");
        message.setStatus(StatusEnum.OK);

        log.info("login : success - "+userModel.get().getName());
        return message;
    }

    public Message signup(SignupDto signupDto) {
        Message message = new Message();
        String phoneNumber = signupDto.getPhoneNumber();
        userRepository.findUserModelByPhoneNumber(phoneNumber);

        if(userRepository.findUserModelByPhoneNumber(phoneNumber).isPresent()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("PhoneNumber is already signed up");
            log.error("signup : PhoneNumber is already signed up");
            return message;
        }

        String password_encode = encoder.encode(signupDto.getPassword());
        String name = signupDto.getName();

        UserModel userModel = new UserModel(phoneNumber,password_encode,name);
        userRepository.save(userModel);

        message.setStatus(StatusEnum.OK);
        message.setMessage("Signup Success");

        log.info("signup : success");
        return message;
    }

    public Message refreshToken(RefreshDto refreshDto) {
        Message message = new Message();

        if(JwtUtil.isExpired(refreshDto.getRefreshToken())) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("RefreshToken is expired");
            log.error("refreshToken : RefreshToken is expired");
            return message;
        }

        String phoneNumber = refreshDto.getPhoneNumber();
        Optional<UserModel> optionalUser = userRepository.findUserModelByPhoneNumber(phoneNumber);

        if(optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            TokenModel tokenModel = userModel.getTokenModel();
            if(Objects.equals(tokenModel.getRefreshToken(), refreshDto.getRefreshToken())) {
                String accessToken = JwtUtil.generateToken(userModel);
                tokenModel.setAccessToken(accessToken);
                userModel.setTokenModel(tokenModel);
                userRepository.save(userModel);

                message.setStatus(StatusEnum.OK);
                message.setMessage("Refresh Success");

                log.info("refreshToken : Refresh Success");
                return message;
            }
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("RefreshToken is not correct");
            log.error("refreshToken : RefreshToken is not correct");
            return message;
        }

        message.setStatus(StatusEnum.UNAUTHORIZED);
        message.setMessage("Not Found User");
        log.error("refreshToken : Not Found User");
        return message;
    }

    public Boolean checkAccessToken(String phoneNumber ,String accessToken) {
        Optional<UserModel> optionalUserModel = userRepository.findUserModelByPhoneNumber(phoneNumber);

        if(optionalUserModel.isPresent()) {
            TokenModel tokenModel = optionalUserModel.get().getTokenModel();

            return Objects.equals(tokenModel.getAccessToken(), accessToken);
        }

        return false;
    }
}
