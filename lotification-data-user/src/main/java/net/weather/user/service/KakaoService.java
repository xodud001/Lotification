package net.weather.user.service;


import lombok.RequiredArgsConstructor;
import net.weather.kakao.api.response.GetKakaoUserResponse;
import net.weather.kakao.service.KakaoLoginService;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoUserRepository kakaoUserRepository;

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

        if( !isPresentKakaoUser(kakaoUser.id()) ){
            userService.save(user);
        }

        return findKakaoUserByKakaoId(kakaoUser.id());
    }

    public boolean isPresentKakaoUser(String kakaoId){
        return kakaoUserRepository.findByKakaoId(kakaoId).isPresent();
    }

    public KakaoUser findKakaoUserByKakaoId(String id) {
        return kakaoUserRepository.findByKakaoId(id)
                .orElseThrow(() -> new IllegalStateException(id + "카카오 유저가 존재하지 않습니다."));
    }
}
