package net.weather.alarm.alarm.service;

import jakarta.persistence.PersistenceContext;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.exception.AlarmNotFoundException;
import net.weather.alarm.alarm.exception.AlreadyContainsUserException;
import net.weather.alarm.alarm.exception.ExistAlarmException;
import net.weather.alarm.alarm.repository.AlarmRepository;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.AlarmTargetRepository;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.push_token.domain.PushToken;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlarmServiceTest{

    @PersistenceContext
    TestEntityManager em;

    @TestConfiguration
    static class TestConfig{

        @Bean
        public AlarmService alarmService(AlarmRepository alarmRepository, AlarmTargetRepository alarmTargetRepository){
            return new AlarmService(alarmRepository, alarmTargetRepository);
        }

    }

    @Autowired
    AlarmService alarmService;

    @BeforeEach
    void init(){
        Summoner summoner1 = Summoner.builder().name("날씨는 우중충1")
                .id("SUMMONER_ID1")
                .revisionDate(Instant.now())
                .puuid("PUUID1")
                .accountId("ACCOUNT_ID1")
                .build();
        em.persist(summoner1);

        Summoner summoner2 = Summoner.builder().name("날씨는 우중충2")
                .id("SUMMONER_ID2")
                .revisionDate(Instant.now())
                .puuid("PUUID2")
                .accountId("ACCOUNT_ID2")
                .build();
        em.persist(summoner2);

        Summoner summoner3 = Summoner.builder().name("날씨는 우중충3")
                .id("SUMMONER_ID3")
                .revisionDate(Instant.now())
                .puuid("PUUID3")
                .accountId("ACCOUNT_ID3")
                .build();
        em.persist(summoner3);

        KakaoUser kakaoUser = new KakaoUser("kimtaeyoung", "freeptk1@gmail.com", "KAKAO_ID", "ACCESS_TOKEN", "REFRESH_TOKEN");
        em.persist(kakaoUser);

        Alarm alarm1 = Alarm.builder()
                .monitoringTarget(summoner1)
                .build();
        em.persist(alarm1);

        Alarm alarm2 = Alarm.builder()
                .monitoringTarget(summoner2)
                .build();
        em.persist(alarm2);

        Alarm alarm3 = Alarm.builder()
                .monitoringTarget(summoner3)
                .build();
        em.persist(alarm3);

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

        em.persist(alarmTarget1);
        em.persist(alarmTarget2);
        em.persist(alarmTarget3);

        em.flush();
        em.clear();
    }


    @Test
    void find_by_monitoring_target(){
        Alarm alarm = alarmService.findByMonitoringTarget("SUMMONER_ID1");

        assertThat(alarm.getMonitoringTarget().getId()).isEqualTo("SUMMONER_ID1");
        assertThat(alarm.getId()).isGreaterThan(0L);
    }

    @Test
    void not_found_by_monitoring_target(){
        assertThatThrownBy(() -> alarmService.findByMonitoringTarget("NOT_FOUND_ID"))
                .isInstanceOf(AlarmNotFoundException.class);
    }

    @Test
    void present_true(){
        assertThat(alarmService.isPresent("SUMMONER_ID1")).isTrue();
    }

    @Test
    void present_false(){
        assertThat(alarmService.isPresent("NOT_FOUND_ID")).isFalse();
    }

    @Test
    void create(){
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        Long alarmId = alarmService.createAlarm(summoner);
        Alarm alarm = alarmService.findById(alarmId);

        assertThat(alarm.getMonitoringTarget().getId()).isEqualTo(summoner.getId());
    }

    @Test
    void duplicate_create(){
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        alarmService.createAlarm(summoner);
        assertThatThrownBy(() -> alarmService.createAlarm(summoner)).isInstanceOf(ExistAlarmException.class);
    }

    @Test
    void regist_target_to_alarm(){
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        Long alarmId = alarmService.createAlarm(summoner);
        Alarm alarm = alarmService.findById(alarmId);

        User user = new User("TEST_USER", "TEST_EMAIL");
        em.persist(user);
        em.flush();
        em.clear();

        Long targetId = alarmService.createAlarmTarget(user, alarm);

        List<AlarmTarget> targets = alarmService.findById(alarmId).getAlarmTargets();

        assertThat(targets.size()).isEqualTo(1);
        AlarmTarget alarmTarget = targets.get(0);

        assertThat(alarmTarget.getId()).isEqualTo(targetId);
    }

    @Test
    void duplicate_regist_target() {
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        Long alarmId = alarmService.createAlarm(summoner);
        Alarm alarm = alarmService.findById(alarmId);

        User user = new User("TEST_USER", "TEST_EMAIL");
        em.persist(user);
        em.flush();
        em.clear();

        alarmService.createAlarmTarget(user, alarm);
        assertThatThrownBy(() -> alarmService.createAlarmTarget(user, alarm)).isInstanceOf(AlreadyContainsUserException.class);
    }

    @Test
    void find_targets(){
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        Long alarmId = alarmService.createAlarm(summoner);
        Alarm alarm = alarmService.findById(alarmId);

        User user = new User("TEST_USER", "TEST_EMAIL");
        em.persist(user);
        alarmService.createAlarmTarget(user, alarm);
        em.flush();
        em.clear();

        List<AlarmTargetDto> alarmTargets = alarmService.getAlarmTargets(user.getId());

        assertThat(alarmTargets.size()).isEqualTo(1);
        AlarmTargetDto targetDto = alarmTargets.get(0);

        assertThat(targetDto.getAlarmId()).isEqualTo(alarmId);
        assertThat(targetDto.getSummonerName()).isEqualTo("TEST_SUMMONER");
    }

    @Test
    void not_found_alarm(){
        assertThatThrownBy(() -> alarmService.findById(-1L)).isInstanceOf(AlarmNotFoundException.class);
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

        Long alarmId = alarmService.createAlarm(summoner);
        Alarm alarm = alarmService.findById(alarmId);

        User user = new User("TEST_USER", "TEST_EMAIL");
        em.persist(user);

        PushToken pushToken = new PushToken("TEST_PUSH_TOKEN");
        pushToken.joinUser(user);
        em.persist(pushToken);

        alarmService.createAlarmTarget(user, alarm);

        em.flush();
        em.clear();

        //when
        List<SendAlarmTargetDto> sendAlarms = alarmService.getSendAlarms(alarmId);

        //then
        assertThat(sendAlarms.size()).isEqualTo(1);
        SendAlarmTargetDto sendAlarmTargetDto = sendAlarms.get(0);
        assertThat(sendAlarmTargetDto.getPushToken()).isEqualTo("TEST_PUSH_TOKEN");
    }

    @Test
    void delete_target(){
        Summoner summoner = Summoner.builder().name("TEST_SUMMONER")
                .id("TEST_SUMMONER_ID")
                .revisionDate(Instant.now())
                .puuid("TEST_PUUID")
                .accountId("TEST_ACCOUNT_ID")
                .build();
        em.persist(summoner);

        Long alarmId = alarmService.createAlarm(summoner);
        Alarm alarm = alarmService.findById(alarmId);

        User user = new User("TEST_USER", "TEST_EMAIL");
        em.persist(user);
        Long targetId = alarmService.createAlarmTarget(user, alarm);
        em.flush();
        em.clear();

        assertThat(alarmService.getAlarmTargets(user.getId()).size()).isEqualTo(1);

        alarmService.deleteTargets(targetId);

        assertThat(alarmService.getAlarmTargets(user.getId()).size()).isEqualTo(0);
    }
}