package net.weather.alarm.alarm_target.repository;

import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;

import java.util.List;

public interface AlarmQueryTargetRepository {

    List<AlarmTargetDto> getAlarmTargets(Long userId);
}
