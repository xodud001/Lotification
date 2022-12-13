package net.weather.monitor.consumer.consumer;

import io.github.jav.exposerversdk.ExpoPushMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.service.AlarmService;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;
import net.weather.monitor.consumer.notification.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmEventConsumer {

    private final AlarmService alarmService;
    private final NotificationService notificationService;
    private final static String TOPIC = "lotification";

    @KafkaListener(topics = TOPIC, groupId = "lotification", containerFactory = "lotificationKafkaListenerContainerFactory")
    public void listen(List<String> alarms){
        List<ExpoPushMessage> messages = new ArrayList<>();

        for (String alarmId : alarms) {

            if(isNotNumber(alarmId)){
                continue;
            }

            Alarm alarm = alarmService.findById(Long.parseLong(alarmId));

            List<SendAlarmTargetDto> sendAlarms = alarmService.getSendAlarms(alarm.getId());
            for (SendAlarmTargetDto sendAlarm : sendAlarms) {
                ExpoPushMessage message = new ExpoPushMessage();
                message.setSubtitle("lotification");
                message.getTo().add(sendAlarm.getPushToken());
                message.setTitle("게임 시작 알림");
                message.setBody(String.format("소환사 %s님께서 게임을 시작하셨습니다.", alarm.getMonitoringTarget().getName()) );
                messages.add(message);
            }
        }
        notificationService.sendNotification(messages);
    }

    private boolean isNotNumber(String alarmId) {
        try{
            Long.parseLong(alarmId);
            return false;
        }catch (NumberFormatException ignore){
            return true;
        }
    }
}
