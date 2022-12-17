package net.weather.monitor.producer.scheduler;

import lombok.RequiredArgsConstructor;
import net.weather.monitor.producer.job.GameAlarmJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringScheduler {

    private final GameAlarmJob gameAlarmJob;

    @Scheduled(cron = "*/30 * * * * *")
    public void monitoring(){
        gameAlarmJob.execute();
    }
}
