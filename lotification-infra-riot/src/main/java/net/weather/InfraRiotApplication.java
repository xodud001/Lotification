package net.weather;

import lombok.RequiredArgsConstructor;
import net.weather.riot.RiotApi;
import net.weather.riot.response.CurrentGameInfoResponse;
import net.weather.riot.response.SummonerResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootApplication
@RequiredArgsConstructor
public class InfraRiotApplication {

    private final RiotApi riotApi;

    public static void main(String[] args) {
        SpringApplication.run(InfraRiotApplication.class);
    }

}
