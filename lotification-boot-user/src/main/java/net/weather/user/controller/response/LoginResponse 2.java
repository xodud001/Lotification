package net.weather.user.controller.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
