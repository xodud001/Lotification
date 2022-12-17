package net.weather.user.service;

import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.kakao.api.response.KakaoAccount;
import net.weather.kakao.api.response.PropertiesResponse;
import net.weather.kakao.service.KakaoLoginService;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import net.weather.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class KakaoServiceTest {

    @Autowired
    EntityManager em;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8")
            .withDatabaseName("lotification")
            .withUsername("admin")
            .withPassword("1234");

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
        public UserService userService(UserRepository userRepository, KakaoUserRepository kakaoUserRepository){
            return new UserService(userRepository, kakaoUserRepository);
        }

        @Bean
        public KakaoService kakaoService(UserService userService){
            return new KakaoService(userService, kakaoLoginService());
        }
    }

    @Autowired
    KakaoService kakaoService;

    @Autowired
    UserService userService;

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

        KakaoUser kakaoUser = userService.findKakaoUserByKakaoId(response.id());

        assertThat(kakaoUser.getId()).isEqualTo(savedUser.getId());
        assertThat(kakaoUser.getEmail()).isEqualTo(savedUser.getEmail());

    }

}