package net.weather.api.user.controller.request;

public record KakaoLoginRequest(
        String accessToken,
        String refreshToken
) {

}
