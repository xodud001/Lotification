package net.weather.api.user.controller.request;

public record CreateUserRequest(
        String name,
        String email) {

}
