package net.weather.kakao.api;


import net.weather.kakao.api.request.GetTokenRequest;
import net.weather.kakao.api.response.GetTokenResponse;
<<<<<<< HEAD
import net.weather.kakao.api.response.GetUserResponse;
=======
import net.weather.kakao.api.response.GetKakaoUserResponse;
>>>>>>> feat_15
import net.weather.kakao.config.KakaoProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoApi {

    private final KakaoProperty property;
    private final WebClient client;

    public KakaoApi(KakaoProperty property) {
        this.property = property;
        this.client = WebClient.builder()
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();
    }

    public GetTokenResponse getToken(String code){
        GetTokenRequest request = new GetTokenRequest(property.getGrantType(), property.getApiKey(), property.getRedirectUrl(), code);
        MultiValueMap<String, String> body = request.toForm();

        return this.client.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(GetTokenResponse.class)
                .block();
    }

<<<<<<< HEAD
    public GetUserResponse getUser(String accessToken){
=======
    public GetKakaoUserResponse getUser(String accessToken){
>>>>>>> feat_15
        return this.client.get()
                .uri("https://kapi.kakao.com//v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
<<<<<<< HEAD
                .bodyToMono(GetUserResponse.class)
=======
                .bodyToMono(GetKakaoUserResponse.class)
>>>>>>> feat_15
                .block();
    }
}
