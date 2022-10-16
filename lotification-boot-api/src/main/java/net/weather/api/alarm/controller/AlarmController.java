package net.weather.api.alarm.controller;


import lombok.RequiredArgsConstructor;
import net.weather.api.alarm.controller.request.CreateAlarmRequest;
import net.weather.api.alarm.service.MainAlarmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final MainAlarmService alarmService;

    @PostMapping("/alarms")
    public void createAlarm(@RequestBody CreateAlarmRequest request){
        alarmService.createAlarm(request);
    }
}
