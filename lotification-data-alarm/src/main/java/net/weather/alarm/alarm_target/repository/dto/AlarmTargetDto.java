package net.weather.alarm.alarm_target.repository.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class AlarmTargetDto {

    private Long id;
    private Long alarmId;
    private String summonerName;

    @QueryProjection
    public AlarmTargetDto(Long id, Long alarmId, String summonerName) {
        this.id = id;
        this.alarmId = alarmId;
        this.summonerName = summonerName;
    }
}
