package net.weather.alarm.alarm_target.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.weather.alarm.alarm.domain.QAlarm;
import net.weather.alarm.alarm_target.domain.QAlarmTarget;
import net.weather.alarm.alarm_target.repository.dto.AlarmTargetDto;
import net.weather.alarm.alarm_target.repository.dto.SendAlarmTargetDto;
import net.weather.lol.summoner.domain.QSummoner;
import net.weather.push_token.domain.QPushToken;
import net.weather.user.domain.QUser;

import javax.persistence.EntityManager;
import java.util.List;

import static net.weather.alarm.alarm.domain.QAlarm.*;
import static net.weather.alarm.alarm_target.domain.QAlarmTarget.alarmTarget;
import static net.weather.lol.summoner.domain.QSummoner.*;
import static net.weather.push_token.domain.QPushToken.*;
import static net.weather.user.domain.QUser.*;

public class AlarmTargetRepositoryImpl implements AlarmQueryTargetRepository{

    private final JPAQueryFactory query;
    private final EntityManager em;

    public AlarmTargetRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<AlarmTargetDto> getAlarmTargets(Long userId) {
        return query.select(Projections.constructor(AlarmTargetDto.class,
                        alarmTarget.id,
                        alarm.id,
                        summoner.name,
                        summoner.summonerLevel,
                        alarm.lastPlayTime))
                .from(alarmTarget)
                .join(alarmTarget.alarm, alarm)
                .join(alarm.monitoringTarget, summoner)
                .where(alarmTarget.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<SendAlarmTargetDto> getSendAlarmTargets(Long alarmId) {
        return query.select(Projections.constructor(SendAlarmTargetDto.class, pushToken.token))
                .from(alarmTarget)
                .join(alarmTarget.user, user)
                .join(user.tokens, pushToken)
                .where(alarmTarget.alarm.id.eq(alarmId))
                .fetch();
    }
}
