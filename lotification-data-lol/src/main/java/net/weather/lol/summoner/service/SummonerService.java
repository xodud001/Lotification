package net.weather.lol.summoner.service;

import lombok.RequiredArgsConstructor;
import net.weather.lol.summoner.domain.Summoner;
import net.weather.lol.summoner.exception.SummonerNotFoundException;
import net.weather.lol.summoner.repository.SummonerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerRepository summonerRepository;

    public Summoner findByNickname(String nickname){
        return summonerRepository.findByName(nickname)
                .orElseThrow(() -> new SummonerNotFoundException(nickname + " 소환사가 존재하지 않습니다."));
    }

    public boolean isPresent(String nickname){
        Optional<Summoner> summoner = summonerRepository.findByName(nickname);
        return summoner.isPresent();
    }

    @Transactional
    public String save(Summoner summoner){
        Summoner savedSummoner = summonerRepository.save(summoner);
        return savedSummoner.getId();
    }

}
