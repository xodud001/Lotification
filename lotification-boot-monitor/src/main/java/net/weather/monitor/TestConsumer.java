package net.weather.monitor;

import io.github.jav.exposerversdk.ExpoPushMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.service.AlarmService;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;
import net.weather.monitor.notification.NotificationService;
import net.weather.push_token.domain.PushToken;
import net.weather.user.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestConsumer {

    private final AlarmService alarmService;
    private final NotificationService notificationService;
    private final static String TOPIC = "lotification";

    @KafkaListener(topics = TOPIC, groupId = "lotification")
    public void listen(String alarmId){
        log.info("alarm Id={}", alarmId);
        Alarm alarm = alarmService.findById(Long.valueOf(alarmId));

        List<ExpoPushMessage> messages = new ArrayList<>();

        List<SendAlarmTargetDto> sendAlarms = alarmService.getSendAlarms(alarm.getId());
        for (SendAlarmTargetDto sendAlarm : sendAlarms) {
            ExpoPushMessage message = new ExpoPushMessage();
            message.getTo().add(sendAlarm.getPushToken());
            message.setTitle("게임 시작 알림");
            message.setBody(String.format("소환사 %s님께서 게임을 시작하셨습니다.", alarm.getMonitoringTarget().getName()) );
            messages.add(message);
        }
        notificationService.sendNotification(messages);
    }
}
