package net.weather.api.alarm.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import net.weather.api.alarm.controller.request.CreateAlarmRequest;
import net.weather.api.alarm.service.MainAlarmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
}
