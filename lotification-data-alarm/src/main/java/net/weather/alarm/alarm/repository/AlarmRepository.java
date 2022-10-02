package net.weather.alarm.alarm.repository;

import net.weather.alarm.alarm.domain.Alarm;
import net.weather.lol.summoner.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmQueryRepository {

}
