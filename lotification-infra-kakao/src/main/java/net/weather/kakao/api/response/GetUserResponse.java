package net.weather.kakao.api.response;

import java.time.LocalDateTime;

public record GetUserResponse(
        String id,
        Boolean hasSignedUp,
        LocalDateTime connectedAt,
        LocalDateTime synchedAt,
        PropertiesResponse properties,
        KakaoAccount kakaoAccount
) {

}
