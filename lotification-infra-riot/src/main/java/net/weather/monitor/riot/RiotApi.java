package net.weather.monitor.riot;

import net.weather.monitor.riot.response.SummonerResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class RiotApi implements SummonerApi{

    private final WebClient client;

    public RiotApi() {
        this.client = WebClient.builder()
                .baseUrl("https://kr.api.riotgames.com")
                .build();
    }

    @Override
    public SummonerResponse findSummonerByNickname(String nickname) {
        String uri = "/lol/summoner/v4/summoners/by-name/" + URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        return client.get()
                .uri(URI.create(uri))
                .retrieve()
                .bodyToMono(SummonerResponse.class)
                .block();
    }

    @Override
    public SummonerResponse findSummonerByAccountId(String accountId) {
        return null;
    }

    @Override
    public SummonerResponse findSummonerByUUID(String uuid) {
        return null;
    }

    @Override
    public SummonerResponse findSummonerById(String id) {
        return null;
    }

}
