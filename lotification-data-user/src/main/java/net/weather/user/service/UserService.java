package net.weather.user.service;

import lombok.RequiredArgsConstructor;
import net.weather.user.domain.KakaoUser;
import net.weather.user.domain.User;
import net.weather.user.repository.KakaoUserRepository;
import net.weather.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final KakaoUserRepository kakaoUserRepository;

    public User findById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException(id + "유저를 찾지 못했습니다."));
    }

    @Transactional
    public Long save(User user){
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public boolean isPresentKakaoUser(String kakaoId){
        return kakaoUserRepository.findByKakaoId(kakaoId).isPresent();
    }

    public KakaoUser findKakaoUserByKakaoId(String id) {
        return kakaoUserRepository.findByKakaoId(id)
                .orElseThrow(() -> new IllegalStateException(id + "카카오 유저가 존재하지 않습니다."));
    }
}
