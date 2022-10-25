package net.weather.alarm.alarm_event.domain;

import lombok.*;
import net.weather.alarm.alarm.domain.Alarm;

import javax.persistence.*;
import java.time.Instant;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class AlarmEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;

    private Instant gameStartTime;

    private Instant issuedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    public void joinAlarm(Alarm alarm) {
        this.alarm = alarm;
        alarm.getAlarmEvents().add(this);
    }
}
