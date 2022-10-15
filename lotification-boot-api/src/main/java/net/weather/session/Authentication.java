package net.weather.session;

public record Authentication(
        String accessToken,
        String refreshToken
) {

}
