package net.weather.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import net.weather.monitor.job.GameAlarmJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringScheduler {

    private final GameAlarmJob gameAlarmJob;

    @Scheduled(cron = "* */2 * * * *")
    public void monitoring(){
        gameAlarmJob.execute();
    }
}
