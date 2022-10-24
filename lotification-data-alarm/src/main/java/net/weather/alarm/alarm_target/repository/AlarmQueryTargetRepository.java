package net.weather.alarm.alarm_target.repository;

import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;

import java.util.List;

public interface AlarmQueryTargetRepository {

    List<AlarmTargetDto> getAlarmTargets(Long userId);

    List<SendAlarmTargetDto> getSendAlarmTargets(Long alarmId);
}
