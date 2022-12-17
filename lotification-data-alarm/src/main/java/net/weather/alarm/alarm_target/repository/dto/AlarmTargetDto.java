package net.weather.alarm.alarm_target.repository.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.Instant;

@Getter
public class AlarmTargetDto {

    private Long id;
    private Long alarmId;
    private String summonerName;
    private Long summonerLevel;
    private Instant lastPlayTime;

    @QueryProjection
    public AlarmTargetDto(Long id, Long alarmId, String summonerName, Long summonerLevel, Instant lastPlayTime) {
        this.id = id;
        this.alarmId = alarmId;
        this.summonerName = summonerName;
        this.summonerLevel = summonerLevel;
        this.lastPlayTime = lastPlayTime;
    }
}
