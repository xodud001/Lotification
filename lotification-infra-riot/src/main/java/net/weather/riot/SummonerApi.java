package net.weather.riot;

import net.weather.riot.response.SummonerResponse;

public interface SummonerApi {

    SummonerResponse findSummonerByNickname(String nickname);

    SummonerResponse findSummonerByAccountId(String accountId);

    SummonerResponse findSummonerByUUID(String uuid);

    SummonerResponse findSummonerById(String id);
}
