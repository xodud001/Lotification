package net.weather.monitor.job;

import net.weather.alarm.alarm.domain.Alarm;
import net.weather.riot.response.CurrentGameInfoResponse;

public record JobContext(Alarm alarm,
                         CurrentGameInfoResponse currentGame
) {
}
