package net.weather.alarm.alarm_event.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmEvent is a Querydsl query type for AlarmEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmEvent extends EntityPathBase<AlarmEvent> {

    private static final long serialVersionUID = -205172375L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmEvent alarmEvent = new QAlarmEvent("alarmEvent");

    public final net.weather.alarm.alarm.domain.QAlarm alarm;

    public final NumberPath<Long> gameId = createNumber("gameId", Long.class);

    public final DateTimePath<java.time.Instant> gameStartTime = createDateTime("gameStartTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> issuedAt = createDateTime("issuedAt", java.time.Instant.class);

    public QAlarmEvent(String variable) {
        this(AlarmEvent.class, forVariable(variable), INITS);
    }

    public QAlarmEvent(Path<? extends AlarmEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmEvent(PathMetadata metadata, PathInits inits) {
        this(AlarmEvent.class, metadata, inits);
    }

    public QAlarmEvent(Class<? extends AlarmEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new net.weather.alarm.alarm.domain.QAlarm(forProperty("alarm"), inits.get("alarm")) : null;
    }

}

