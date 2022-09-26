package net.weather.user.service;

import lombok.RequiredArgsConstructor;
import net.weather.user.domain.User;
import net.weather.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException(id + "유저를 찾지 못했습니다."));
    }

    public Long save(User user){
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
