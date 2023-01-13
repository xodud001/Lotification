package net.weather.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.kakao.api.KakaoApi;
import net.weather.kakao.api.response.GetKakaoUserResponse;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoApi api;

    public GetKakaoUserResponse authorize(String accessToken){
        return api.getUser(accessToken);
    }
}
