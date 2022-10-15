package net.weather.push_token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.push_token.domain.PushToken;
import net.weather.push_token.repository.PushTokenRepository;
import net.weather.user.domain.User;
import net.weather.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PushToken pushToken = new PushToken(token);

        pushToken.joinUser(user);
        PushToken savedToken = tokenRepository.save(pushToken);

        return savedToken.getId();
    }

    public PushToken findToken(Long id) {
        return tokenRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " 토큰이 존재하지 않습니다."));
    }
}
