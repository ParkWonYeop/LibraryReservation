package com.example.libraryreservation.auth;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.auth.dto.RefreshDto;
import com.example.libraryreservation.auth.dto.SignupDto;
import com.example.libraryreservation.common.jwt.JwtUtil;
import com.example.libraryreservation.common.model.TokenModel;
import com.example.libraryreservation.common.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.libraryreservation.common.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.libraryreservation.common.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenRepository tokenRepository;
    @Value("${jwt.secret_key}")
    private String secretKey;

    public TokenModel login(LoginDto loginDto) {
        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(loginDto.getPhoneNumber());
        if(userModel.isEmpty()) {
            throw new AccessDeniedException("user not found");
        }

        if(!encoder.matches(loginDto.getPassword(), userModel.get().getPassword())) {
            throw new AccessDeniedException("Password is not matched");
        }

        String accessToken = JwtUtil.generateToken(userModel.get(), secretKey);
        String refreshToken = JwtUtil.createRefreshToken(secretKey);

        Optional<TokenModel> tokenModelOptional = tokenRepository.findTokenModelByUserModel(userModel.get());
        TokenModel tokenModel = tokenModelOptional.orElseGet(TokenModel::new);

        tokenModel.setUserModel(userModel.get());
        tokenModel.setAccessToken(accessToken);
        tokenModel.setRefreshToken(refreshToken);

        tokenRepository.save(tokenModel);

        log.info("login : success - "+userModel.get().getName());
        return tokenModel;
    }

    public String signup(SignupDto signupDto) {
        String phoneNumber = signupDto.getPhoneNumber();
        userRepository.findUserModelByPhoneNumber(phoneNumber);

        if(userRepository.findUserModelByPhoneNumber(phoneNumber).isPresent()) {
            throw new AccessDeniedException("phoneNumber is already sign up");
        }

        String password_encode = encoder.encode(signupDto.getPassword());
        String name = signupDto.getName();

        UserModel userModel = new UserModel();
        userModel.setPhoneNumber(phoneNumber);
        userModel.setPassword(password_encode);
        userModel.setName(name);
        userRepository.save(userModel);

        return "success";
    }

    public String refreshToken(RefreshDto refreshDto) {
        if(JwtUtil.isExpired(refreshDto.getRefreshToken(), secretKey)) {
            throw new AccessDeniedException("refreshToken is expired");
        }

        String phoneNumber = refreshDto.getPhoneNumber();
        Optional<UserModel> optionalUser = userRepository.findUserModelByPhoneNumber(phoneNumber);

        if(optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            Optional<TokenModel> tokenModel = tokenRepository.findTokenModelByUserModel(userModel);
            if(tokenModel.isEmpty()) {
                throw new AccessDeniedException("token not found");
            }

            if(Objects.equals(tokenModel.get().getRefreshToken(), refreshDto.getRefreshToken())) {
                String accessToken = JwtUtil.generateToken(userModel, secretKey);
                tokenModel.get().setAccessToken(accessToken);
                tokenRepository.save(tokenModel.get());

                return accessToken;
            }
            throw new AccessDeniedException("refreshToken is not correct");
        }
        throw new AccessDeniedException("user not found");
    }


    public String checkToken() {
        return "success";
    }
}
