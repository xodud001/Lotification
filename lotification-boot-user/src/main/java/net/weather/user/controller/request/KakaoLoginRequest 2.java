package net.weather.user.controller.request;

public record KakaoLoginRequest(
        String accessToken,
        String refreshToken
) {

}
