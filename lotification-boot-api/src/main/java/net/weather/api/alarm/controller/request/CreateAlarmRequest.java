package net.weather.api.alarm.controller.request;


public record CreateAlarmRequest(Long userId, String targetNickname) {

}
