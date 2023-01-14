package net.weather.api.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.weather.api.user.controller.request.CreateTokenRequest;
import net.weather.api.user.controller.request.KakaoLoginRequest;
import net.weather.api.config.JwtProperties;
import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.push_token.domain.PushToken;
import net.weather.push_token.service.PushTokenService;
import net.weather.api.user.controller.response.GetPushTokenResponse;
import net.weather.api.user.controller.response.LoginResponse;
import net.weather.user.domain.User;
import net.weather.user.service.KakaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;
    private final PushTokenService tokenService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login/kakao")
    public LoginResponse loginByKakao(@RequestBody KakaoLoginRequest request){
        GetKakaoUserResponse kakaoUser = kakaoService.authorize(request.accessToken());
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
                .withClaim("nickname", user.getName())
                .sign(algorithm);
    }

    @GetMapping("/push-token/{id}")
    public GetPushTokenResponse getToken(@PathVariable Long id){
        PushToken token = tokenService.findById(id);
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
