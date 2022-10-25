package net.weather.lol.summoner.repository;

import net.weather.lol.summoner.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, String> {

    Optional<Summoner> findByName(String nickname);
}
