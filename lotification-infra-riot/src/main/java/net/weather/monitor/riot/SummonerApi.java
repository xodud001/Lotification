package net.weather.monitor.riot;

import net.weather.monitor.riot.response.SummonerResponse;

public interface SummonerApi {

    SummonerResponse findSummonerByNickname(String nickname);

    SummonerResponse findSummonerByAccountId(String accountId);

    SummonerResponse findSummonerByUUID(String uuid);

    SummonerResponse findSummonerById(String id);
}
