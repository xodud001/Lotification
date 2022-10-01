package net.weather.riot;

import net.weather.riot.response.CurrentGameInfoResponse;
import net.weather.riot.response.SummonerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RiotApiTest {

    @Autowired
    RiotApi riotApi;

    @Test
    void current_game_info(){
        String nickname = "Kuri8";
        SummonerResponse summoner = riotApi.findSummonerByNickname(nickname);
        System.out.println(summoner);
        CurrentGameInfoResponse game = riotApi.findCurrentGameById(summoner.id());
        System.out.println(game);
        System.out.println(game.startDateTime());
    }
}