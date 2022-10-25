package net.weather.api.session;

public record Authentication(
        String accessToken,
        String refreshToken
) {

}
