package net.weather.alarm.alarm.exception;

public class AlreadyContainsUserException extends RuntimeException{

    public AlreadyContainsUserException(String message) {
        super(message);
    }
}
