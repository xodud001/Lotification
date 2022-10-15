package net.weather.summoner.controller;

import lombok.RequiredArgsConstructor;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.lol.summoner.service.SummonerService;
import net.weather.riot.RiotApi;
import net.weather.riot.response.SummonerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SummonerController {

    private final RiotApi riotApi;
    private final SummonerService summonerService;

    @GetMapping("/summoner/{nickname}")
    public SummonerResponse findSummoner(@PathVariable("nickname") String nickname){
        return riotApi.findSummonerByNickname(nickname);
    }
}
