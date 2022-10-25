package net.weather.kakao.api.response;

public record KakaoAccount(
        Boolean profileNicknameNeedsAgreement,
        ProfileResponse profile,
        Boolean hasEmail,
        Boolean emailNeedsAgreement,
        Boolean isEmailValid,
        Boolean isEmailVerified,
        String email
) {
}
