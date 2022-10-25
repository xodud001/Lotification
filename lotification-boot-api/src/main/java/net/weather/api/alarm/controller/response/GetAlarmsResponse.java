package net.weather.api.alarm.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetAlarmsResponse {

    private List<AlarmTargetDto> alarms;
}
