package net.weather.user.service;


import lombok.RequiredArgsConstructor;
import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.kakao.service.KakaoLoginService;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    public GetKakaoUserResponse authorize(String accessToken) {
        return kakaoLoginService.authorize(accessToken);
    }


    public User loginAndSave(GetKakaoUserResponse kakaoUser, String accessToken, String refreshToken) {
        String email = "";
        if(kakaoUser.kakaoAccount() != null){
            email = kakaoUser.kakaoAccount().email();
        }

        KakaoUser user = new KakaoUser(kakaoUser.properties().nickname(),
                email,
                kakaoUser.id(),
                accessToken,
                refreshToken);

        if( !userService.isPresentKakaoUser(kakaoUser.id()) ){
            userService.save(user);
        }

        return userService.findKakaoUserByKakaoId(kakaoUser.id());
    }
}
