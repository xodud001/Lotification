package net.weather.api.alarm.service;

import lombok.RequiredArgsConstructor;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.service.AlarmService;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.api.alarm.controller.request.CreateAlarmRequest;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.lol.summoner.service.SummonerService;
import net.weather.riot.RiotApi;
import net.weather.riot.response.SummonerResponse;
import net.weather.user.domain.User;
import net.weather.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainAlarmService {

    private final UserService userService;
    private final SummonerService summonerService;
    private final RiotApi riotApi;
    private final AlarmService alarmService;

    @Transactional
    public void createAlarm(Long userId, CreateAlarmRequest request) {
        boolean isSummonerPresent = summonerService.isPresent(request.targetNickname());
        if (!isSummonerPresent) {
            SummonerResponse summonerResponse = riotApi.findSummonerByNickname(request.targetNickname());
            Instant revisionDate = Instant.ofEpochMilli(summonerResponse.revisionDate());

            Summoner summoner = Summoner.builder()
                    .id(summonerResponse.id())
                    .accountId(summonerResponse.accountId())
                    .puuid(summonerResponse.puuid())
                    .name(summonerResponse.name())
                    .summonerLevel(summonerResponse.summonerLevel())
                    .profileIconId(summonerResponse.profileIconId())
                    .revisionDate(revisionDate).build();
            summonerService.save(summoner);
        }

        Summoner summoner = summonerService.findByNickname(request.targetNickname());
        boolean isAlarmPresent = alarmService.isPresent(summoner.getId());
        if (!isAlarmPresent) {
            alarmService.createAlarm(summoner);
        }

        User user = userService.findById(userId);
        Alarm alarm = alarmService.findByMonitoringTarget(summoner.getId());
        alarmService.createAlarmTarget(user, alarm);

    }

    public List<AlarmTargetDto> getAlarmTargets(Long userId) {
        return alarmService.getAlarmTargets(userId);
    }
}
