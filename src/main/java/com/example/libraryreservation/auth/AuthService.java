package com.example.libraryreservation.auth;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.auth.dto.RefreshDto;
import com.example.libraryreservation.auth.dto.RefreshResponseDto;
import com.example.libraryreservation.auth.dto.SignupDto;
import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.jwt.JwtUtil;
import com.example.libraryreservation.common.model.TokenModel;
import com.example.libraryreservation.common.model.UserModel;
import com.example.libraryreservation.common.repository.TokenRepository;
import com.example.libraryreservation.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TokenModel login(LoginDto loginDto) {
        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(loginDto.phoneNumber());
        if (userModel.isEmpty()) {
            throw new AccessDeniedException("전화번호가 일치하지 않습니다.");
        }

        if (!encoder.matches(loginDto.password(), userModel.get().getPassword())) {
            throw new AccessDeniedException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = JwtUtil.generateToken(userModel.get(), secretKey);
        String refreshToken = JwtUtil.createRefreshToken(secretKey);

        Optional<TokenModel> tokenModelOptional = tokenRepository.findTokenModelByUserModel(userModel.get());
        TokenModel tokenModel = tokenModelOptional.orElseGet(TokenModel::new);

        tokenModel.setUserModel(userModel.get());
        tokenModel.setAccessToken(accessToken);
        tokenModel.setRefreshToken(refreshToken);

        tokenRepository.save(tokenModel);

        log.info("login : success - " + userModel.get().getName());
        return tokenModel;
    }

    @Transactional
    public void signup(SignupDto signupDto) {
        String phoneNumber = signupDto.phoneNumber();
        userRepository.findUserModelByPhoneNumber(phoneNumber);

        if (userRepository.findUserModelByPhoneNumber(phoneNumber).isPresent()) {
            throw new LibraryReservationException(CommunalResponse.ALREADY_SIGNUP_PHONENUMBER);
        }

        String password_encode = encoder.encode(signupDto.password());
        String name = signupDto.name();

        UserModel userModel = new UserModel();
        userModel.setPhoneNumber(phoneNumber);
        userModel.setPassword(password_encode);
        userModel.setName(name);
        userRepository.save(userModel);
    }

    @Transactional
    public RefreshResponseDto refreshToken(RefreshDto refreshDto) {
        if (JwtUtil.isExpired(refreshDto.refreshToken(), secretKey)) {
            throw new AccessDeniedException("refreshToken is expired");
        }

        String phoneNumber = refreshDto.phoneNumber();
        Optional<UserModel> optionalUser = userRepository.findUserModelByPhoneNumber(phoneNumber);

        if (optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            Optional<TokenModel> tokenModel = tokenRepository.findTokenModelByUserModel(userModel);
            if (tokenModel.isEmpty()) {
                throw new AccessDeniedException("token not found");
            }

            if (Objects.equals(tokenModel.get().getRefreshToken(), refreshDto.refreshToken())) {
                String accessToken = JwtUtil.generateToken(userModel, secretKey);
                String refreshToken = JwtUtil.createRefreshToken(secretKey);
                tokenModel.get().setAccessToken(accessToken);
                tokenModel.get().setRefreshToken(refreshToken);
                tokenRepository.save(tokenModel.get());

                return new RefreshResponseDto(accessToken,refreshToken);
            }
            throw new AccessDeniedException("refreshToken is not correct");
        }
        throw new AccessDeniedException("user not found");
    }
}
