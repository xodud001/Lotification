package net.weather.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.config.JwtProperties;
import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.push_token.domain.PushToken;
import net.weather.push_token.service.PushTokenService;
import net.weather.user.SessionConst;
import net.weather.user.controller.request.CreateTokenRequest;
import net.weather.user.controller.request.CreateUserRequest;
import net.weather.user.controller.request.KakaoLoginRequest;
import net.weather.user.controller.response.GetPushTokenResponse;
import net.weather.user.controller.response.LoginResponse;
import net.weather.user.controller.response.UserResponse;
import net.weather.user.domain.User;
import net.weather.user.service.KakaoService;
import net.weather.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final PushTokenService tokenService;
    private final JwtProperties jwtProperties;

    @GetMapping("/users/{id}")
    public UserResponse findById(@PathVariable("id") Long id){
        User user = userService.findById(id);
        return new UserResponse(user.getId(), user.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseEntity<UserResponse> save(@RequestBody CreateUserRequest request){
        User user = new User(request.name(), request.email());
        Long savedUserId = userService.save(user);

        return ResponseEntity
                .created( URI.create("/users/"+savedUserId) )
                .build();
    }

    @PostMapping("/login/kakao")
    public LoginResponse loginByKakao(@RequestBody KakaoLoginRequest request, HttpServletRequest httpRequest){
        GetKakaoUserResponse kakaoUser = kakaoService.authorize(request.accessToken());
        log.info("session id(login)={}", httpRequest.getSession().getId() );

        User user = kakaoService.loginAndSave(kakaoUser, request.accessToken(), request.refreshToken());

        String accessToken = generateAccessToken(user);

        return new LoginResponse(accessToken, request.refreshToken());
    }

    private String generateAccessToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
        Instant issuedAt = Instant.now();
        return JWT.create()
                .withIssuer("lotification")
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plusSeconds(60L * 360L))
                .withClaim("userId", user.getId())
                .sign(algorithm);
    }

    @GetMapping("/push-token/{id}")
    public GetPushTokenResponse getToken(@PathVariable Long id, @RequestHeader("Authorization") String authorization){
        PushToken token = tokenService.findToken(id);
        return new GetPushTokenResponse(token.getToken());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/push-token")
    public ResponseEntity<Void> createToken(@RequestBody CreateTokenRequest request, @RequestHeader("Authorization") String authorization){
        String token = authorization.substring(7);
        DecodedJWT decodedJWT = JWT.decode(token);
        Long userId = decodedJWT.getClaim("userId").asLong();

        Long tokenId = tokenService.createToken(userId, request.pushToken());
        log.info("save token: {}", request.pushToken());
        return ResponseEntity.created(URI.create("/push-token/" + tokenId)).build();
    }

}
