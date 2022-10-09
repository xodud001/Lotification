package net.weather.kakao.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record GetTokenRequest(
        @JsonProperty("grant_type") String grantType,
        @JsonProperty("client_id") String clientId,
        @JsonProperty("redirect_uri") String redirectUri,
        String code
) {

    public MultiValueMap<String, String> toForm(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);

        return body;
    }
}
