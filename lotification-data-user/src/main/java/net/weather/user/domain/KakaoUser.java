package net.weather.user.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue(value = "KAKAO")
public class KakaoUser extends User {

    String kakaoId;
    String accessToken;
    String refreshToken;

    public KakaoUser(String name, String email, String kakaoId, String accessToken, String refreshToken) {
        super(name, email);
        this.kakaoId = kakaoId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
