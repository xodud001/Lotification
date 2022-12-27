package net.weather.user.service;

import net.weather.user.domain.User;
import net.weather.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
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

import static org.assertj.core.api.Assertions.*;


//@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserServiceTest {

    @Autowired
    EntityManager em;

//    @Container
//    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8")
//            .withDatabaseName("lotification")
//            .withUsername("admin")
//            .withPassword("1234");


    @TestConfiguration
    static class TestConfig{

        @Bean
        public UserService userService(UserRepository userRepository){
            return new UserService(userRepository);
        }

    }

    @Autowired
    UserService userService;

    @Test
    void save_and_find_user(){
        User user = new User("test_user", "test@email.com");
        Long userId = userService.save(user);

        User findUser = userService.findById(userId);

        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

}