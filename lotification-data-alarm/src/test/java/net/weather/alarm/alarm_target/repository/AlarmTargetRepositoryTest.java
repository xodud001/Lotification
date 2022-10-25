package net.weather.alarm.alarm_target.repository;

import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.repository.AlarmRepository;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.lol.summoner.repository.SummonerRepository;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import net.weather.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Testcontainers
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlarmTargetRepositoryTest {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8")
            .withDatabaseName("lotification")
            .withUsername("admin")
            .withPassword("1234");

    @Autowired
    AlarmTargetRepository alarmTargetRepository;

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    SummonerRepository summonerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    KakaoUserRepository kakaoUserRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void find_alarm_targets() {
        Summoner summoner1 = Summoner.builder().name("날씨는 우중충1")
                .id("SUMMONER_ID1")
                .revisionDate(Instant.now())
                .puuid("PUUID1")
                .accountId("ACCOUNT_ID1")
                .build();
        summonerRepository.save(summoner1);

        Summoner summoner2 = Summoner.builder().name("날씨는 우중충2")
                .id("SUMMONER_ID2")
                .revisionDate(Instant.now())
                .puuid("PUUID2")
                .accountId("ACCOUNT_ID2")
                .build();
        summonerRepository.save(summoner2);

        Summoner summoner3 = Summoner.builder().name("날씨는 우중충3")
                .id("SUMMONER_ID3")
                .revisionDate(Instant.now())
                .puuid("PUUID3")
                .accountId("ACCOUNT_ID3")
                .build();
        summonerRepository.save(summoner3);

        KakaoUser kakaoUser = new KakaoUser("kimtaeyoung", "freeptk1@gmail.com", "KAKAO_ID", "ACCESS_TOKEN", "REFRESH_TOKEN");
        kakaoUserRepository.save(kakaoUser);

        Alarm alarm1 = Alarm.builder()
                .monitoringTarget(summoner1)
                .build();
        alarmRepository.save(alarm1);

        Alarm alarm2 = Alarm.builder()
                .monitoringTarget(summoner1)
                .build();
        alarmRepository.save(alarm2);

        Alarm alarm3 = Alarm.builder()
                .monitoringTarget(summoner1)
                .build();
        alarmRepository.save(alarm3);

        AlarmTarget alarmTarget1 = AlarmTarget.builder()
                .user(kakaoUser)
                .build();
        AlarmTarget alarmTarget2 = AlarmTarget.builder()
                .user(kakaoUser)
                .build();
        AlarmTarget alarmTarget3 = AlarmTarget.builder()
                .user(kakaoUser)
                .build();

        alarmTarget1.joinAlarm(alarm1);
        alarmTarget2.joinAlarm(alarm2);
        alarmTarget3.joinAlarm(alarm3);

        alarmTargetRepository.save(alarmTarget1);
        alarmTargetRepository.save(alarmTarget2);
        alarmTargetRepository.save(alarmTarget3);

        em.flush();
        em.clear();

        List<AlarmTargetDto> alarmTargets = alarmTargetRepository.getAlarmTargets(kakaoUser.getId());

        assertThat(alarmTargets.size()).isEqualTo(3);
    }
}