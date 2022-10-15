package net.weather.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.kakao.api.KakaoApi;
<<<<<<< HEAD
import net.weather.kakao.api.response.GetTokenResponse;
import net.weather.kakao.api.response.GetUserResponse;
=======
import net.weather.kakao.api.response.GetKakaoUserResponse;
>>>>>>> feat_15
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoApi api;

<<<<<<< HEAD
    public boolean authorize(String code){
        GetTokenResponse tokenResponse = api.getToken(code);
        log.info("Token Response={}", tokenResponse);
        GetUserResponse user = api.getUser(tokenResponse.accessToken());
        log.info("User Response={}", user);

        return true;
=======
    public GetKakaoUserResponse authorize(String accessToken){
        GetKakaoUserResponse user = api.getUser(accessToken);
        log.info("User Response={}", user);

        return user;
>>>>>>> feat_15
    }
}
