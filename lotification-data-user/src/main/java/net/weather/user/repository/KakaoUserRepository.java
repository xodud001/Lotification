package net.weather.user.repository;

import net.weather.user.domain.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, Long> {

    Optional<KakaoUser> findByKakaoId(String kakaoId);
}
