package net.weather.user.controller;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import net.weather.kakao.service.KakaoLoginService;
import net.weather.user.controller.request.CreateUserRequest;
import net.weather.user.controller.request.KakaoLoginRequest;
=======

import lombok.extern.slf4j.Slf4j;

import net.weather.user.SessionConst;
import net.weather.user.controller.request.CreateUserRequest;
import net.weather.user.controller.request.KakaoLoginRequest;
import net.weather.user.controller.response.LoginResponse;

>>>>>>> feat_15
import net.weather.user.controller.response.UserResponse;
import net.weather.user.domain.User;
import net.weather.user.service.KakaoService;
import net.weather.user.service.UserService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return new UserResponse(user.getId(), user.getName());
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/users")
//    public ResponseEntity<UserResponse> save(@RequestBody CreateUserRequest request){
//        User user = new User(request.name(), request.email());
//        Long savedUserId = userService.save(user);
//
//
//        return ResponseEntity
//                .created( URI.create("/users/"+savedUserId) )
//                .build();
//    }

<<<<<<< HEAD
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
=======
//    @PostMapping("/login/kakao")
//    public LoginResponse loginByKakao(@RequestBody KakaoLoginRequest request, HttpServletRequest httpRequest){
//        GetKakaoUserResponse kakaoUser = kakaoService.authorize(request.accessToken());
//        log.info("kakao login result = {}", kakaoUser);
//
//        User user = kakaoService.loginAndSave(kakaoUser, request.accessToken(), request.refreshToken());
//
//        httpRequest.setAttribute(SessionConst.SESSION_USER, user);
//
//        return new LoginResponse(request.accessToken(), request.refreshToken());
//    }
>>>>>>> feat_15
}
