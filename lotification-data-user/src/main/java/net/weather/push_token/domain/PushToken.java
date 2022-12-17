package net.weather.push_token.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.weather.user.domain.User;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PushToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    private String token;

    public PushToken(String token) {
        this.token = token;
    }

    public void joinUser(User user){
        this.user = user;
        user.getTokens().add(this);
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
