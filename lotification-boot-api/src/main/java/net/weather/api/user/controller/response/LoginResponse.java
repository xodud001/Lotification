package net.weather.api.user.controller.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
