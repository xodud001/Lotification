package net.weather.riot.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record CurrentGameInfoResponse(
    Long gameId,
    String gameType,
    Long gameStartTime,
    Long mapId,
    Long gameLength, // 게임이 시작된 후 경과된 시간(초)
    String platformId,
    String gameMode,
    Long gameQueueConfigId
) {
    public LocalDateTime startDateTime(){
        return LocalDateTime.ofEpochSecond(gameStartTime()/1000, 0, ZoneOffset.ofHours(9));
    }

}
