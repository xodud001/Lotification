package net.weather.push_token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.push_token.domain.PushToken;
import net.weather.push_token.exception.DuplicationTokenException;
import net.weather.push_token.exception.PushTokenNotFoundException;
import net.weather.push_token.repository.PushTokenRepository;
import net.weather.user.domain.User;
import net.weather.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PushTokenService {

    private final UserService userService;
    private final PushTokenRepository tokenRepository;

    @Transactional
    public Long createToken(Long userId, String token){
        User user = userService.findById(userId);
        Optional<PushToken> tokenOpt = tokenRepository.findByUser(user);

        if(tokenOpt.isPresent()) {
            throw new DuplicationTokenException(user.getId() + " 유저의 토큰이 이미 존재합니다.");
        }

        PushToken pushToken = new PushToken(token);
        pushToken.joinUser(user);
        PushToken savedToken = tokenRepository.save(pushToken);

        return savedToken.getId();
    }

    public PushToken findById(Long id) {
        return tokenRepository.findById(id)
                .orElseThrow(() -> new PushTokenNotFoundException(id + " 토큰이 존재하지 않습니다."));
    }

    public PushToken findByUser(User user){
        return tokenRepository.findByUser(user).orElseThrow(() -> new PushTokenNotFoundException(user.getId() + " 유저의 토큰이 존재하지 않습니다."));
    }
}
