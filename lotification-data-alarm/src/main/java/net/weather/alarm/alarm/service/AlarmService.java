package net.weather.alarm.alarm.service;

import lombok.RequiredArgsConstructor;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.exception.AlarmNotFoundException;
import net.weather.alarm.alarm.exception.AlreadyContainsUserException;
import net.weather.alarm.alarm.exception.ExistAlarmException;
import net.weather.alarm.alarm.repository.AlarmRepository;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.AlarmTargetRepository;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmTargetRepository alarmTargetRepository;

    public Alarm findByMonitoringTarget(String summonerId){
        return alarmRepository.findByMonitoringTarget_Id(summonerId)
                .orElseThrow(() -> new AlarmNotFoundException(summonerId + " 소환사에 대한 알람이 없습니다."));
    }

    public Alarm findBySummonerId(String summonerId){
        return alarmRepository.findBySummonerId(summonerId)
                .orElseThrow(() -> new AlarmNotFoundException(summonerId + " 소환사에 대한 알람이 없습니다."));
    }

    public boolean isPresent(String summonerId){
        Optional<Alarm> alarmOpt = alarmRepository.findBySummonerId(summonerId);
        return alarmOpt.isPresent();
    }

    @Transactional
    public Long createAlarm(Summoner summoner){

        if(isPresent(summoner.getId())){
            throw new ExistAlarmException(summoner.getId() + " 소환사에 대한 알람이 이미 존재합니다.");
        }

        Alarm alarm = Alarm.builder()
                .monitoringTarget(summoner)
                .build();
        Alarm savedAlarm = alarmRepository.save(alarm);

        return savedAlarm.getId();
    }

    @Transactional
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
