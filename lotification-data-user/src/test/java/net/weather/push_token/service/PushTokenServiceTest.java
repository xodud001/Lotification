package net.weather.push_token.service;

import net.weather.push_token.domain.PushToken;
import net.weather.push_token.exception.DuplicationTokenException;
import net.weather.push_token.exception.PushTokenNotFoundException;
import net.weather.push_token.repository.PushTokenRepository;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import net.weather.user.repository.UserRepository;
import net.weather.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PushTokenServiceTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    PushTokenRepository pushTokenRepository;

    @Autowired
    PushTokenService tokenService;

    @Autowired
    UserService userService;

    @TestConfiguration
    static class TestConfig{
        @Bean
        public PushTokenService pushTokenService(PushTokenRepository pushTokenRepository, UserService userService){
            return new PushTokenService(userService, pushTokenRepository);
        }

        @Bean
        public UserService userService(UserRepository userRepository){
            return new UserService(userRepository);
        }
    }

    @Test
    void create_push_token(){
        User user = new User("김태영", "dud708@naver.com");
        userService.save(user);

        Long tokenId = tokenService.createToken(user.getId(), "EXPO_PUSH_NOTIFICATION_TOKEN");

        em.flush();
        em.clear();

        PushToken findToken = tokenService.findById(tokenId);

        assertThat(tokenId).isEqualTo(findToken.getId());
    }

    @Test
    void find_token_user(){
        User user = new User("김태영", "dud708@naver.com");
        userService.save(user);

        Long tokenId = tokenService.createToken(user.getId(), "EXPO_PUSH_NOTIFICATION_TOKEN");

        em.flush();
        em.clear();

        PushToken findToken = tokenService.findByUser(user);

        assertThat(tokenId).isEqualTo(findToken.getId());
    }

    @Test
    void not_found_token(){
        User user = new User("김태영", "dud708@naver.com");
        userService.save(user);
        em.flush();
        em.clear();

        assertThrows(PushTokenNotFoundException.class, () -> tokenService.findById(1L));
        assertThrows(PushTokenNotFoundException.class, () -> tokenService.findByUser(user));
    }

    @Test
    void upsert(){
        User user = new User("김태영", "dud708@naver.com");
        userService.save(user);

        tokenService.createToken(user.getId(), "EXPO_PUSH_NOTIFICATION_TOKEN");
        Long tokenId = tokenService.createToken(user.getId(), "UPSERT_EXPO_PUSH_NOTIFICATION_TOKEN");

        PushToken token = tokenService.findById(tokenId);
        assertThat(token.getToken()).isEqualTo("UPSERT_EXPO_PUSH_NOTIFICATION_TOKEN");
    }
}