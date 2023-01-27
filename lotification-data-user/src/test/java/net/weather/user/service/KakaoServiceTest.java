package net.weather.user.service;

import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.kakao.api.response.KakaoAccount;
import net.weather.kakao.api.response.PropertiesResponse;
import net.weather.kakao.service.KakaoLoginService;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import net.weather.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class KakaoServiceTest {

    @Autowired
    TestEntityManager em;

    @TestConfiguration
    static class Config{

        private KakaoLoginService kakaoLoginService(){
            KakaoLoginService kakaoLoginService = Mockito.mock(KakaoLoginService.class);

            PropertiesResponse properties = new PropertiesResponse("김태영", "dud708@naver.com");
            KakaoAccount account = new KakaoAccount(true,
                                                        null,
                                                      true,
                                            true,
                                                    true,
                                                  true,
                                                         "dud708@naver.com");
            GetKakaoUserResponse response = new GetKakaoUserResponse("USER_ID",
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    properties,
                    account);

            Mockito.when(kakaoLoginService.authorize("ACCESS_TOKEN")).thenReturn(response);

            return kakaoLoginService;
        }

        @Bean
        public UserService userService(UserRepository userRepository) {
            return new UserService(userRepository);
        }

        @Bean
        public KakaoService kakaoService(UserService userService, KakaoUserRepository kakaoUserRepository){
            return new KakaoService(kakaoUserRepository, userService, kakaoLoginService());
        }
    }

    @Autowired
    KakaoService kakaoService;

    @Test
    void authorize(){
        String accessToken = "ACCESS_TOKEN";

        GetKakaoUserResponse response = kakaoService.authorize(accessToken);

        assertThat(response.id()).isEqualTo("USER_ID");
        assertThat(response.kakaoAccount().email()).isEqualTo("dud708@naver.com");
    }

    @Test
    void login_and_save(){
        String accessToken = "ACCESS_TOKEN";
        GetKakaoUserResponse response = kakaoService.authorize(accessToken);

        User savedUser = kakaoService.loginAndSave(response, accessToken, "REFRESH_TOKEN");

        em.flush();
        em.clear();

        KakaoUser kakaoUser = kakaoService.findKakaoUserByKakaoId(response.id());

        assertThat(kakaoUser.getId()).isEqualTo(savedUser.getId());
        assertThat(kakaoUser.getEmail()).isEqualTo(savedUser.getEmail());

    }

}