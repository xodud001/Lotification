package net.weather.alarm.alarm_event.repository;

import net.weather.alarm.alarm_event.domain.AlarmEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
}
