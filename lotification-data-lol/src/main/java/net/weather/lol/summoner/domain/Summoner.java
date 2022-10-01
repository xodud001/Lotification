package net.weather.lol.summoner.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
public class Summoner {

    @Id
    private String id;

    private String accountId;

    private String puuid;

    private String name;

    private Instant revisionDate; // 마지막으로 수정된 날짜

}
