package net.weather.alarm.alarm_target.repository.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SendAlarmTargetDto {

    private final String pushToken;

    @QueryProjection
    public SendAlarmTargetDto(String pushToken) {
        this.pushToken = pushToken;
    }
}
