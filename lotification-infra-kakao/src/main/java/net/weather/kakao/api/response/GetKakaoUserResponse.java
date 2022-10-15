package net.weather.kakao.api.response;

import java.time.LocalDateTime;

public record GetKakaoUserResponse(
        String id,
        Boolean hasSignedUp,
        LocalDateTime connectedAt,
        LocalDateTime synchedAt,
        PropertiesResponse properties,
        KakaoAccount kakaoAccount
) {

}
