package net.weather.user.controller.request;

import lombok.Getter;

public record CreateUserRequest(
        String name,
        String email) {

}
