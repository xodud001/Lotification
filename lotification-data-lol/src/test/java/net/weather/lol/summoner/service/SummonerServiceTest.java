package net.weather.lol.summoner.service;

import net.weather.lol.summoner.domain.Summoner;
import net.weather.lol.summoner.exception.SummonerNotFoundException;
import net.weather.lol.summoner.repository.SummonerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class SummonerServiceTest {

    @PersistenceContext
    EntityManager em;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public SummonerService summonerService(SummonerRepository summonerRepository){
            return new SummonerService(summonerRepository);
        }
    }

    @Autowired
    SummonerService summonerService;

    @Test
    void save_and_find_by_nickname(){
        String name = "TEST_NAME";
        Summoner summoner = Summoner.builder()
                .id("SUMMONER_ID")
                .accountId("ACCOUNT_ID")
                .puuid("PUUID")
                .name(name)
                .revisionDate(Instant.now())
                .summonerLevel(200L)
                .profileIconId(10).build();

        summonerService.save(summoner);

        Summoner findSummoner = summonerService.findByNickname(name);

        assertThat(findSummoner.getId()).isEqualTo(summoner.getId());
        assertThat(findSummoner.getAccountId()).isEqualTo(summoner.getAccountId());
        assertThat(findSummoner.getPuuid()).isEqualTo(summoner.getPuuid());
        assertThat(findSummoner.getSummonerLevel()).isEqualTo(summoner.getSummonerLevel());
        assertThat(findSummoner.getProfileIconId()).isEqualTo(summoner.getProfileIconId());
    }

    @Test
    void not_found_by_nickname(){
        assertThatThrownBy(() -> summonerService.findByNickname("NOT_FOUND_NICKNAME"))
                .isInstanceOf(SummonerNotFoundException.class);
    }


}