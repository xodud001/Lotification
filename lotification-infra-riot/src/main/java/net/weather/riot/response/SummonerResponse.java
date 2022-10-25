package net.weather.riot.response;

public record SummonerResponse(
        String id,
        String accountId,
        String puuid,
        String name,
        Integer profileIconId,
        Long revisionDate,
        Long summonerLevel
) {
}
