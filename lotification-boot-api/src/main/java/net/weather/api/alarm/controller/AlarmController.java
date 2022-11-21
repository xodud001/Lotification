package net.weather.api.alarm.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.api.alarm.controller.request.CreateAlarmRequest;
import net.weather.api.alarm.controller.response.GetAlarmsResponse;
import net.weather.api.alarm.service.MainAlarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final MainAlarmService alarmService;

    @PostMapping("/alarms")
    public void createAlarm(@RequestBody CreateAlarmRequest request,
                            @RequestHeader("Authorization") String authorization){
        String token = authorization.substring(7);
        DecodedJWT decodedJWT = JWT.decode(token);
        Long userId = decodedJWT.getClaim("userId").asLong();

        alarmService.createAlarm(userId, request);
    }

    @GetMapping("/alarms")
    public GetAlarmsResponse getAlarms(@RequestHeader("Authorization") String authorization){
        String token = authorization.substring(7);
        DecodedJWT decodedJWT = JWT.decode(token);
        Long userId = decodedJWT.getClaim("userId").asLong();

        List<AlarmTargetDto> alarmTargets = alarmService.getAlarmTargets(userId);
        return new GetAlarmsResponse(alarmTargets);
    }

    @DeleteMapping("/alarms/targets/{id}")
    public ResponseEntity<Void> deleteAlarmTargets(@PathVariable("id") Long targetId){
        alarmService.deleteAlarmTarget(targetId);

        return ResponseEntity.noContent().build();
    }
}
