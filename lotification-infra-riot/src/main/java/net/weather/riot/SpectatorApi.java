package net.weather.riot;

import net.weather.riot.response.CurrentGameInfoResponse;
import net.weather.riot.response.SummonerResponse;

public interface SpectatorApi {

    CurrentGameInfoResponse findCurrentGameById(String id);
}
