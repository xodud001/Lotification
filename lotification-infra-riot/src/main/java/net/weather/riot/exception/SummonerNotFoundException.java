package net.weather.riot.exception;

public class SummonerNotFoundException extends RuntimeException{

    public SummonerNotFoundException(String message) {
        super(message);
    }
}
