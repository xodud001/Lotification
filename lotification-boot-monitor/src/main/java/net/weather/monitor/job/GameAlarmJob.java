package net.weather.monitor.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.service.AlarmService;
import net.weather.alarm.alarm_event.service.AlarmEventService;
import net.weather.riot.RiotApi;
import net.weather.riot.response.CurrentGameInfoResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameAlarmJob {

    private final AlarmService alarmService;
    private final AlarmEventService eventService;

    private final RiotApi riotApi;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC = "lotification";


    @Transactional
    public void execute(){
        List<Alarm> alarms = alarmService.findAll();
        log.debug("size of all alarms={}", alarms.size());

        List<JobContext> contexts = findPlayings(alarms);
        log.debug("size of playing={}", contexts.size());

        List<JobContext> alarmTargets = findTargets(contexts);
        log.debug("size of targets={}", alarmTargets.size());

        publishEvent(alarmTargets);
    }

    private List<JobContext> findPlayings(List<Alarm> alarms) {
        List<JobContext> contexts = new ArrayList<>();

        for (Alarm alarm : alarms) {
            try{
                CurrentGameInfoResponse currentGame = riotApi.findCurrentGameById(alarm.getMonitoringTarget().getId());
                contexts.add(new JobContext(alarm, currentGame));
            }catch(RuntimeException e){
                log.error("소환사 조회 실패", e);
            }
        }
        return contexts;
    }

    private List<JobContext> findTargets(List<JobContext> context) {
        List<JobContext> targets = new ArrayList<>();
        for (JobContext jobContext : context) {
            CurrentGameInfoResponse currentGame = jobContext.currentGame();
            Alarm alarm = jobContext.alarm();

            if(!eventService.isPresent(currentGame.gameId())){
                Instant startTime = Instant.ofEpochMilli(currentGame.gameStartTime());
                alarm.updateLastPlayTime(startTime);
                eventService.create(currentGame.gameId(), startTime, alarm);
                targets.add(jobContext);
            }
        }
        return targets;
    }

    private void publishEvent(List<JobContext> alarmTargets) {

        for (JobContext alarmTarget : alarmTargets) {
            kafkaTemplate.send(TOPIC, alarmTarget.alarm().getId().toString());
            log.debug("publish event to alarm {}", alarmTarget.alarm().getId().toString());
        }
    }

}
