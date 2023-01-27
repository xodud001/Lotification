package net.weather.alarm.alarm.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import net.weather.alarm.alarm.domain.Alarm;

import java.util.List;
import java.util.Optional;

import static net.weather.alarm.alarm.domain.QAlarm.alarm;
import static net.weather.lol.summoner.domain.QSummoner.*;

public class AlarmRepositoryImpl implements AlarmQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public AlarmRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Alarm> findByMonitoringTarget(String summonerId) {
        Alarm findAlarm = queryFactory.select(alarm)
                .from(alarm)
                .leftJoin(alarm.monitoringTarget, summoner).fetchJoin()
                .where(summoner.id.eq(summonerId))
                .fetchOne();

        return Optional.ofNullable(findAlarm);
    }

    @Override
    public List<Alarm> findAllWithMonitoringTargets() {
        return queryFactory.select(alarm)
                .from(alarm)
                .leftJoin(alarm.monitoringTarget, summoner).fetchJoin()
                .fetch();
    }
}
