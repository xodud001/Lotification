package net.weather.alarm.alarm_event.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.exception.AlarmNotFoundException;
import net.weather.alarm.alarm_event.domain.AlarmEvent;
import net.weather.alarm.alarm_event.repository.AlarmEventRepository;
import net.weather.lol.summoner.domain.Summoner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlarmEventServiceTest {

    @TestConfiguration
    static class TestConfig{

        @Bean
        public AlarmEventService alarmEventService(AlarmEventRepository alarmEventRepository){
            return new AlarmEventService(alarmEventRepository);
        }
    }

    @PersistenceContext
    EntityManager em;

    @Autowired
    AlarmEventService alarmEventService;

    @Test
    void not_found_alarm_event(){
        assertThatThrownBy(() -> alarmEventService.findById(-1L)).isInstanceOf(AlarmNotFoundException.class);
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

        Alarm alarm = Alarm.builder()
                .monitoringTarget(summoner)
                .build();
        em.persist(alarm);
        em.flush();
        em.clear();

        Long eventId = alarmEventService.create(1L, Instant.now(), alarm);

        AlarmEvent event = alarmEventService.findById(eventId);

        assertThat(event.getGameId()).isEqualTo(1L);
        assertThat(event.getAlarm().getId()).isEqualTo(alarm.getId());
    }

}