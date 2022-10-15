package net.weather.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.weather.push_token.domain.PushToken;
import org.hibernate.id.UUIDGenerationStrategy;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "SNS_TYPE")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;
    private String name;

    private String email;

<<<<<<< HEAD
    public User(String name) {
=======
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PushToken> tokens = new ArrayList<>();

    public User(String name, String email) {
>>>>>>> feat_15
        this.name = name;
        this.email = email;
    }


}
