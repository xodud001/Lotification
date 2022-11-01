package net.weather.alarm.alarm_target.repository.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * net.weather.alarm.alarm_target.repository.dto.QSendAlarmTargetDto is a Querydsl Projection type for SendAlarmTargetDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSendAlarmTargetDto extends ConstructorExpression<SendAlarmTargetDto> {

    private static final long serialVersionUID = 364599121L;

    public QSendAlarmTargetDto(com.querydsl.core.types.Expression<String> pushToken) {
        super(SendAlarmTargetDto.class, new Class<?>[]{String.class}, pushToken);
    }

}

