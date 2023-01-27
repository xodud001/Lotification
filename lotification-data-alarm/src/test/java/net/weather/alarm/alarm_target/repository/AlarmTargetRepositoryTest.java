package net.weather.alarm.alarm_target.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.repository.AlarmRepository;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.lol.summoner.repository.SummonerRepository;
import net.weather.push_token.domain.PushToken;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import net.weather.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlarmTargetRepositoryTest {

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

    @BeforeEach
    void init(){
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
                .monitoringTarget(summoner2)
                .build();
        alarmRepository.save(alarm2);

        Alarm alarm3 = Alarm.builder()
                .monitoringTarget(summoner3)
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
    }

    @Test
    void find_alarm_targets() {
        KakaoUser kakaoUser = kakaoUserRepository.findByKakaoId("KAKAO_ID").orElseThrow();
        List<AlarmTargetDto> alarmTargets = alarmTargetRepository.getAlarmTargets(kakaoUser.getId());

        assertThat(alarmTargets.size()).isEqualTo(3);
    }

    @Test
    void find_push_token_of_target(){
        // given
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        Alarm alarm = Alarm.builder()
                .monitoringTarget(summoner)
                .build();
        em.persist(alarm);

        User user = new User("TEST_USER", "TEST_EMAIL");
        em.persist(user);

        PushToken pushToken = new PushToken("TEST_PUSH_TOKEN");
        pushToken.joinUser(user);
        em.persist(pushToken);

        AlarmTarget alarmTarget = AlarmTarget.builder()
                .user(user)
                .build();
        alarmTarget.joinAlarm(alarm);
        em.persist(alarmTarget);

        em.flush();
        em.clear();

        //when
        List<SendAlarmTargetDto> sendAlarms = alarmTargetRepository.getSendAlarmTargets(alarm.getId());

        //then
        assertThat(sendAlarms.size()).isEqualTo(1);
        SendAlarmTargetDto sendAlarmTargetDto = sendAlarms.get(0);
        assertThat(sendAlarmTargetDto.getPushToken()).isEqualTo("TEST_PUSH_TOKEN");
    }


}