package net.weather.api.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.weather.api.user.controller.request.KakaoLoginRequest;
import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.kakao.api.response.KakaoAccount;
import net.weather.kakao.api.response.ProfileResponse;
import net.weather.kakao.api.response.PropertiesResponse;
import net.weather.kakao.service.KakaoLoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;


    @MockBean
    KakaoLoginService kakaoLoginService;

    @Autowired
    ObjectMapper objectMapper;

    GetKakaoUserResponse getKakaoUserResponse(){
        PropertiesResponse properties = new PropertiesResponse("TEST_USER", "TEST_EMAIL@naver.com");
        KakaoAccount kakaoAccount = new KakaoAccount(true,
                new ProfileResponse("TEST_USER"),
                true,
                true,
                true,
                true,
                "TEST_EMAIL@naver.com");
        return new GetKakaoUserResponse("KAKAO_ID",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                properties,
                kakaoAccount
        );
    }

    @Test
    void login_by_kakao() throws Exception {
        String accessToken = "ACCESS_TOKEN";
        String refreshToken = "REFRESH_TOKEN";
        Mockito.when(kakaoLoginService.authorize(accessToken)).thenReturn(getKakaoUserResponse());

        KakaoLoginRequest request = new KakaoLoginRequest(accessToken, refreshToken);
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/login/kakao").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("login-kakao",
                        requestFields(
                                fieldWithPath("accessToken").description("Kakao Auth 서버에서 받은 Access Token"),
                                fieldWithPath("refreshToken").description("Kakao Auth 서버에서 받은 Refresh Token")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("서버에서 JWT 형태로 재생성한 Access Token. 실제 서비스 API 호출시 Authorize 헤더에 첨부"),
                                fieldWithPath("refreshToken").description("Kakao Auth 서버에서 받은 Refresh Token")
                        )
                ));
    }
}