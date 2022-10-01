package net.weather;

import lombok.RequiredArgsConstructor;
import net.weather.monitor.riot.RiotApi;
import net.weather.monitor.riot.response.SummonerResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.URLEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class InfraRiotApplication {

    private final RiotApi api;

    public static void main(String[] args) {
        SpringApplication.run(InfraRiotApplication.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void call(){

        SummonerResponse summoner = api.findSummonerByNickname("날씨는 우중충");
        System.out.println(summoner);
    }
}
