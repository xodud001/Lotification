package net.weather.alarm.alarm_event.service;

import lombok.RequiredArgsConstructor;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm_event.domain.AlarmEvent;
import net.weather.alarm.alarm_event.repository.AlarmEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmEventService {

    private final AlarmEventRepository eventRepository;

    @Transactional
    public Long create(Long gameId, Long gameStartTime, Alarm alarm){
        AlarmEvent event = AlarmEvent.builder()
                .gameId(gameId)
                .gameStartTime(Instant.ofEpochMilli(gameStartTime))
                .build();

        event.joinAlarm(alarm);

        AlarmEvent savedEvent = eventRepository.save(event);
        return savedEvent.getId();
    }

    public boolean isPresent(Long gameId){
        Optional<AlarmEvent> alarmEventOpt = eventRepository.findByGameId(gameId);

        return alarmEventOpt.isPresent();
    }
}
