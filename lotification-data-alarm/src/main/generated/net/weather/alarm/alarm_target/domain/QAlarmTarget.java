package net.weather.alarm.alarm_target.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmTarget is a Querydsl query type for AlarmTarget
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmTarget extends EntityPathBase<AlarmTarget> {

    private static final long serialVersionUID = -968992929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmTarget alarmTarget = new QAlarmTarget("alarmTarget");

    public final net.weather.alarm.alarm.domain.QAlarm alarm;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final net.weather.user.domain.QUser user;

    public QAlarmTarget(String variable) {
        this(AlarmTarget.class, forVariable(variable), INITS);
    }

    public QAlarmTarget(Path<? extends AlarmTarget> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmTarget(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmTarget(PathMetadata metadata, PathInits inits) {
        this(AlarmTarget.class, metadata, inits);
    }

    public QAlarmTarget(Class<? extends AlarmTarget> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new net.weather.alarm.alarm.domain.QAlarm(forProperty("alarm"), inits.get("alarm")) : null;
        this.user = inits.isInitialized("user") ? new net.weather.user.domain.QUser(forProperty("user")) : null;
    }

}

