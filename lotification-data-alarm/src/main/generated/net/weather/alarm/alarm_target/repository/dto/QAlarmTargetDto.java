package net.weather.alarm.alarm_target.repository.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * net.weather.alarm.alarm_target.repository.dto.QAlarmTargetDto is a Querydsl Projection type for AlarmTargetDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlarmTargetDto extends ConstructorExpression<AlarmTargetDto> {

    private static final long serialVersionUID = 508993449L;

    public QAlarmTargetDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> alarmId, com.querydsl.core.types.Expression<String> summonerName) {
        super(AlarmTargetDto.class, new Class<?>[]{long.class, long.class, String.class}, id, alarmId, summonerName);
    }

}

