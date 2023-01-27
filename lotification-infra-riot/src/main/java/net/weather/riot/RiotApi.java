package net.weather.riot;

import lombok.extern.slf4j.Slf4j;
import net.weather.riot.exception.GameNotFoundException;
import net.weather.riot.exception.SummonerNotFoundException;
import net.weather.riot.response.CurrentGameInfoResponse;
import net.weather.riot.response.SummonerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class RiotApi implements SummonerApi, SpectatorApi {

    private final WebClient client;
    private final RiotProperty property;

    public RiotApi(RiotProperty property) {
        this.property = property;
        this.client = WebClient.builder()
                .baseUrl("https://kr.api.riotgames.com")
                .build();
    }

    @Override
    public SummonerResponse findSummonerByNickname(String nickname) {
        String path = "/lol/summoner/v4/summoners/by-name/" + nickname;
        return getSummoner(path);
    }

    @Override
    public SummonerResponse findSummonerByAccountId(String accountId) {
        String path = "/lol/summoner/v4/summoners/by-account/" + accountId;
        return getSummoner(path);
    }

    @Override
    public SummonerResponse findSummonerByUUID(String puuid) {
        String path = "/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return getSummoner(path);
    }

    @Override
    public SummonerResponse findSummonerById(String id) {
        String path = "/lol/summoner/v4/summoners/" + id;
        return getSummoner(path);
    }

    private SummonerResponse getSummoner(String path) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path).build())
                .header("X-Riot-Token", property.getApiKey())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, res -> {
                    throw new SummonerNotFoundException("소환사가 존재하지 않습니다. " + path);
                })
                .onStatus(HttpStatusCode::is4xxClientError, res -> {
                    throw new IllegalArgumentException("요청이 잘못되었습니다.");
                })
                .onStatus(HttpStatusCode::is5xxServerError, res -> {
                    log.error(res.logPrefix());
                    throw new IllegalStateException("Riot 서버에 문제가 발생했습니다.");
                })
                .bodyToMono(SummonerResponse.class)
                .block();
    }


    @Override
    public CurrentGameInfoResponse findCurrentGameById(String id) {
        String path = "/lol/spectator/v4/active-games/by-summoner/" + id;
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path).build())
                .header("X-Riot-Token", property.getApiKey())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, res -> {
                    throw new GameNotFoundException(id + " 소환사는 현재 게임 중인 소환사가 아닙니다.");
                })
                .onStatus(HttpStatusCode::is4xxClientError, res -> {
                    log.error(res.logPrefix());
                    throw new IllegalArgumentException("요청이 잘못되었습니다.");
                })
                .onStatus(HttpStatusCode::is5xxServerError, res -> {
                    log.error(res.logPrefix());
                    throw new IllegalStateException("Riot 서버에 문제가 발생했습니다.");
                })
                .bodyToMono(CurrentGameInfoResponse.class)
                .block();
    }
}
