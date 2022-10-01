package net.weather.alarm.alarm.service;

import lombok.RequiredArgsConstructor;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.exception.AlreadyContainsUserException;
import net.weather.alarm.alarm.repository.AlarmRepository;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.AlarmTargetRepository;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmTargetRepository alarmTargetRepository;

    public Long createAlarm(Summoner summoner){
        Alarm alarm = Alarm.builder()
                .monitoringTarget(summoner)
                .build();
        Alarm savedAlarm = alarmRepository.save(alarm);

        return savedAlarm.getId();
    }

    public Long createAlarmTarget(User user, Alarm alarm){

        if(alarm.containsUser(user)){
            throw new AlreadyContainsUserException("이미 등록된 사용자입니다.");
        }

        AlarmTarget alarmTarget = AlarmTarget.builder()
                .user(user)
                .build();
        alarmTarget.setAlarm(alarm);

        alarmTargetRepository.save(alarmTarget);
        return alarmTarget.getId();
    }


}
