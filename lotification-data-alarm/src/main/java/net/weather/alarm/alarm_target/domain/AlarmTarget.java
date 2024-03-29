package net.weather.alarm.alarm_target.domain;

import jakarta.persistence.*;
import lombok.*;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
public class AlarmTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    public void joinAlarm(Alarm alarm){
        if(this.alarm != null){
            this.alarm.getAlarmTargets().remove(this);
        }

        this.alarm = alarm;
        alarm.getAlarmTargets().add(this);
    }

}
