package net.weather.alarm.alarm_target.repository;

import net.weather.alarm.alarm_target.domain.AlarmTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmTargetRepository extends JpaRepository<AlarmTarget, Long>, AlarmQueryTargetRepository {

}
