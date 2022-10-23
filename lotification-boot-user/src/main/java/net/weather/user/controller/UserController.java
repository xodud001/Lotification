package net.weather.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.kakao.service.KakaoLoginService;
import net.weather.user.controller.request.CreateUserRequest;
import net.weather.user.controller.request.KakaoLoginRequest;
import net.weather.user.controller.response.UserResponse;
import net.weather.user.domain.User;
import net.weather.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/users/{id}")
    public UserResponse findById(@PathVariable("id") Long id){
        User user = userService.findById(id);
        UserResponse response = new UserResponse(user.getId(), user.getName());
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseEntity<UserResponse> save(@RequestBody CreateUserRequest request){
        User user = new User(request.getName());
        Long savedUserId = userService.save(user);

        return ResponseEntity
                .created( URI.create("/users/"+savedUserId) )
                .build();
    }

    @GetMapping("/login/kakao")
    public void loginByKakao(@ModelAttribute KakaoLoginRequest request){
        log.info("kakao login request = {}", request);
        boolean authorize = kakaoLoginService.authorize(request.code());
        log.info("kakao login result = {}", authorize);
    }
}
