package net.weather.push_token.repository;

import net.weather.push_token.domain.PushToken;
import net.weather.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PushTokenRepository extends JpaRepository<PushToken, Long> {

    Optional<PushToken> findByUser(User user);
}
