package net.weather.alarm.alarm.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import net.weather.alarm.alarm.domain.Alarm;
import net.weather.alarm.alarm.domain.QAlarm;
import net.weather.alarm.alarm_target.domain.QAlarmTarget;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AlarmRepositoryImpl implements AlarmQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public AlarmRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Alarm> findBySummonerId(String summonerId){
        QAlarm a = QAlarm.alarm;

        Alarm alarm = queryFactory.select(a)
                .from(a)
                .where(a.monitoringTarget.id.eq(summonerId))
                .fetchOne();

        return Optional.ofNullable(alarm);
    }
}
