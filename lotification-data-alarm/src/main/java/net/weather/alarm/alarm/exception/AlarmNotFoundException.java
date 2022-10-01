package net.weather.alarm.alarm.exception;

public class AlarmNotFoundException extends RuntimeException{

    public AlarmNotFoundException(String message) {
        super(message);
    }
}
