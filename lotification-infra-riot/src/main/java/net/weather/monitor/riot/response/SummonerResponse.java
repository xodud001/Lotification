package net.weather.monitor.riot.response;

public record SummonerResponse(
        String id,
        String accountId,
        String puuid,
        String name,
        String profileIconId,
        String revisionDate,
        String summonerLevel
) {
}
