package net.weather.alarm.alarm.repository;

import net.weather.alarm.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmQueryRepository{


    Optional<Alarm> findBySummonerId(String summonerId);
}
