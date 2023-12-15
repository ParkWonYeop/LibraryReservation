package com.example.libraryreservation.auth;

import com.example.libraryreservation.dto.LoginDto;
import com.example.libraryreservation.dto.RefreshDto;
import com.example.libraryreservation.dto.SignupDto;
import com.example.libraryreservation.jwt.JwtUtil;
import com.example.libraryreservation.model.TokenModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.libraryreservation.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${jwt.secret_key}")
    private String secretKey;

    public Message login(LoginDto loginDto) {
        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(loginDto.getPhoneNumber());
        Message message = new Message();
        if(userModel.isEmpty()) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("Not Found User");
            log.error("login : Not Found User");
            return message;
        }

        if(!encoder.matches(loginDto.getPassword(), userModel.get().getPassword())) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("Password is not matched");
            log.error("login : Password is not matched");
            return message;
        }

        String accessToken = JwtUtil.generateToken(userModel.get(), secretKey);
        String refreshToken = JwtUtil.createRefreshToken(secretKey);

        TokenModel tokenModel = new TokenModel(accessToken, refreshToken);
        userModel.get().setTokenModel(tokenModel);
        userRepository.save(userModel.get());

        message.setMessage("Login Success");
        message.setStatus(StatusEnum.OK);
        message.setData(tokenModel);

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

        UserModel userModel = new UserModel();
        userModel.setPhoneNumber(phoneNumber);
        userModel.setPassword(password_encode);
        userModel.setName(name);
        userRepository.save(userModel);

        message.setStatus(StatusEnum.OK);
        message.setMessage("Signup Success");

        log.info("signup : success");
        return message;
    }

    public Message refreshToken(RefreshDto refreshDto) {
        Message message = new Message();

        if(JwtUtil.isExpired(refreshDto.getRefreshToken(), secretKey)) {
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
                String accessToken = JwtUtil.generateToken(userModel, secretKey);
                tokenModel.setAccessToken(accessToken);
                userModel.setTokenModel(tokenModel);
                userRepository.save(userModel);

                message.setStatus(StatusEnum.OK);
                message.setMessage("Refresh Success");
                message.setData(accessToken);

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

    public boolean findAccessToken(String token, String phoneNumber) {
        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(phoneNumber);
        if(userModel.isPresent()) {
            String accessToken = userModel.get().getTokenModel().getAccessToken();
            return Objects.equals(accessToken, token);
        }
        return false;
    }
}
