package com.example.libraryreservation.auth;

import com.example.libraryreservation.common.model.TokenModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.libraryreservation.fixture.AuthFixtures.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {
    @Autowired
    private AuthController authController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    protected MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession();

        TokenModel tokenModel = authController.login(loginAddressOne());

        session.setAttribute("refreshToken", tokenModel.getRefreshToken());
    }

    @After
    public void clean() {
        session.clearAttributes();
    }

    @DisplayName("로그인 - 성공")
    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressOne()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.accessToken").isString(),
                        jsonPath("$.refreshToken").isString()
                );
    }

    @DisplayName("로그인 - 빈 전화번호")
    @Test
    public void loginEmptyPhoneTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPhoneNumber("")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("로그인 - 공백")
    @Test
    public void loginBlankTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPhoneNumber(" ")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("로그인 - 특수문자")
    @Test
    public void loginSpecialTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPhoneNumber("*")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("전화번호 형식을 맞춰주세요.")
                );
    }

    @DisplayName("로그인 - 전화번호가 아님")
    @Test
    public void loginNotPhoneNumberTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPhoneNumber("000asd")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("전화번호 형식을 맞춰주세요.")
                );
    }

    @DisplayName("로그인 - 틀린 비밀번호")
    @Test
    public void loginWrongPasswordTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPassword("333333")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호가 일치하지 않습니다.")
                );
    }

    @DisplayName("로그인 - 공백 비밀번호")
    @Test
    public void loginPasswordBlankTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPassword(" ")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("로그인 - 빈 비밀번호")
    @Test
    public void loginPasswordEmptyTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPassword("")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("로그인 - 짧은 비밀번호")
    @Test
    public void loginPasswordShortTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPassword("333")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
                );
    }

    @DisplayName("로그인 - 긴 비밀번호")
    @Test
    public void loginPasswordLongTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPassword("3333333333333")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
                );
    }

    @DisplayName("로그인 - 특수문자")
    @Test
    public void loginPasswordSpecialTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginAddressPassword("33333*")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
                );
    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void SignupTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddress()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated()
                );
    }

    @DisplayName("회원가입 - 짧은 번호")
    @Test
    public void SignupPhoneNumberShortTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPhoneNumber("111")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("전화번호 형식을 맞춰주세요.")
                );
    }

    @DisplayName("회원가입 - 긴 번호")
    @Test
    public void SignupPhoneNumberLongTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPhoneNumber("11111111111111")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("전화번호 형식을 맞춰주세요.")
                );
    }

    @DisplayName("회원가입 - 이미있는 폰번호")
    @Test
    public void SignupAlreadyTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPhoneNumber("01099716733")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("이미 가입한 전화번호입니다.")
                );
    }

    @DisplayName("회원가입 - 짧은 비밀번호")
    @Test
    public void SignupWrongPasswordTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPassword("33333")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
                );
    }

    @DisplayName("회원가입 - 특수문자")
    @Test
    public void SignupSpecialPasswordTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPassword("33333*")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
                );
    }

    @DisplayName("회원가입 - 긴 비밀번호")
    @Test
    public void SignupLongPasswordTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPassword("33333333333333")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
                );
    }

    @DisplayName("회원가입 - 공백 비밀번호")
    @Test
    public void SignupBlankPasswordTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPassword(" ")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("회원가입 - 빈 비밀번호")
    @Test
    public void SignupEmptyPasswordTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressPassword("")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("회원가입 - 빈 이름")
    @Test
    public void SignupEmptyNameTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressName("")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("회원가입 - 공백 이름")
    @Test
    public void SignupBlankNameTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressName(" ")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("빈 문자열 입니다.")
                );
    }

    @DisplayName("회원가입 - 긴 이름")
    @Test
    public void SignupLongNameTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressName("박박박박박")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("이름을 2~4자 사이로 입력해주세요.")
                );
    }

    @DisplayName("회원가입 - 짧은 이름")
    @Test
    public void SignupShortNameTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressName("박")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("이름을 2~4자 사이로 입력해주세요.")
                );
    }

    @DisplayName("회원가입 - 특수문자 이름")
    @Test
    public void SignupSpecialNameTest() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signupAddressName("박원*")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$").value("이름을 2~4자 사이로 입력해주세요.")
                );
    }

    @DisplayName("토큰 새로고침 - 성공")
    @Test
    public void TokenTest() throws Exception {
        mockMvc.perform(put("/auth/token")
                        .content(objectMapper.writeValueAsString(refreshToken(session.getAttribute("refreshToken").toString())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.accessToken").isString(),
                        jsonPath("$.refreshToken").isString()
                );
    }

    @DisplayName("토큰 새로고침 - 잘못된 토큰")
    @Test
    public void TokenWrongTokenTest() throws Exception {
        mockMvc.perform(put("/auth/token")
                        .content(objectMapper.writeValueAsString(refreshToken("1234")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest()
                );
    }
}
