package net.weather.alarm.alarm.domain;

import lombok.*;
import net.weather.alarm.alarm_event.domain.AlarmEvent;
import net.weather.alarm.alarm_target.domain.AlarmTarget;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summoner_id")
    private Summoner monitoringTarget;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alarm")
    private List<AlarmTarget> alarmTargets = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alarm")
    private List<AlarmEvent> alarmEvents = new ArrayList<>();

    public boolean containsUser(User user) {
        for (AlarmTarget target : this.alarmTargets) {
            if (target.getUser().getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }
}
