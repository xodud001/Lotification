package net.weather.alarm.alarm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = -971092308L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final ListPath<net.weather.alarm.alarm_event.domain.AlarmEvent, net.weather.alarm.alarm_event.domain.QAlarmEvent> alarmEvents = this.<net.weather.alarm.alarm_event.domain.AlarmEvent, net.weather.alarm.alarm_event.domain.QAlarmEvent>createList("alarmEvents", net.weather.alarm.alarm_event.domain.AlarmEvent.class, net.weather.alarm.alarm_event.domain.QAlarmEvent.class, PathInits.DIRECT2);

    public final ListPath<net.weather.alarm.alarm_target.domain.AlarmTarget, net.weather.alarm.alarm_target.domain.QAlarmTarget> alarmTargets = this.<net.weather.alarm.alarm_target.domain.AlarmTarget, net.weather.alarm.alarm_target.domain.QAlarmTarget>createList("alarmTargets", net.weather.alarm.alarm_target.domain.AlarmTarget.class, net.weather.alarm.alarm_target.domain.QAlarmTarget.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final net.weather.lol.summoner.domain.QSummoner monitoringTarget;

    public QAlarm(String variable) {
        this(Alarm.class, forVariable(variable), INITS);
    }

    public QAlarm(Path<? extends Alarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarm(PathMetadata metadata, PathInits inits) {
        this(Alarm.class, metadata, inits);
    }

    public QAlarm(Class<? extends Alarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.monitoringTarget = inits.isInitialized("monitoringTarget") ? new net.weather.lol.summoner.domain.QSummoner(forProperty("monitoringTarget")) : null;
    }

}

