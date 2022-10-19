package net.weather.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKakaoUser is a Querydsl query type for KakaoUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKakaoUser extends EntityPathBase<KakaoUser> {

    private static final long serialVersionUID = -1231111396L;

    public static final QKakaoUser kakaoUser = new QKakaoUser("kakaoUser");

    public final QUser _super = new QUser(this);

    public final StringPath accessToken = createString("accessToken");

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath kakaoId = createString("kakaoId");

    //inherited
    public final StringPath name = _super.name;

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final ListPath<net.weather.push_token.domain.PushToken, net.weather.push_token.domain.QPushToken> tokens = _super.tokens;

    public QKakaoUser(String variable) {
        super(KakaoUser.class, forVariable(variable));
    }

    public QKakaoUser(Path<? extends KakaoUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKakaoUser(PathMetadata metadata) {
        super(KakaoUser.class, metadata);
    }

}

