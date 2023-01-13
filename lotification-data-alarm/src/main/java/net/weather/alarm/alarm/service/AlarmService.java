package net.weather.alarm.alarm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.exception.AlarmNotFoundException;
import net.weather.alarm.alarm.exception.AlreadyContainsUserException;
import net.weather.alarm.alarm.exception.ExistAlarmException;
import net.weather.alarm.alarm.repository.AlarmRepository;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.AlarmTargetRepository;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmTargetRepository alarmTargetRepository;

    public List<Alarm> findAll(){
        return alarmRepository.findAllWithMonitoringTargets();
    }

    public Alarm findByMonitoringTarget(String summonerId){
        return alarmRepository.findByMonitoringTarget(summonerId)
                .orElseThrow(() -> new AlarmNotFoundException(summonerId + " 소환사에 대한 알람이 없습니다."));
    }

    public boolean isPresent(String summonerId){
        Optional<Alarm> alarmOpt = alarmRepository.findByMonitoringTarget(summonerId);
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
        alarmTarget.joinAlarm(alarm);
        AlarmTarget savedTarget = alarmTargetRepository.save(alarmTarget);

        return savedTarget.getId();
    }

    public List<AlarmTargetDto> getAlarmTargets(Long userId) {
        return alarmTargetRepository.getAlarmTargets(userId);
    }

    public Alarm findById(Long alarmId) {
        return alarmRepository.findById(alarmId)
                .orElseThrow(() -> new AlarmNotFoundException(alarmId + " 알람이 존재하지 않습니다."));
    }

    public List<SendAlarmTargetDto> getSendAlarms(Long alarmId){
        return alarmTargetRepository.getSendAlarmTargets(alarmId);
    }

    @Transactional
    public void deleteTargets(Long targetId){
        alarmTargetRepository.deleteById(targetId);
    }

    @Transactional
    public void updatePlayTime(Long alarmId, Instant startTime){
        Alarm alarm = findById(alarmId);
        Instant prev = alarm.getLastPlayTime();
        alarm.updateLastPlayTime(startTime);
        log.debug("play time of alarm {} is updated from {} to {}.",
                alarm.getId(),
                prev,
                alarm.getLastPlayTime());
    }
}
