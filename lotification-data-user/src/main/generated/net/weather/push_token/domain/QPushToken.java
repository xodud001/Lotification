package net.weather.push_token.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPushToken is a Querydsl query type for PushToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPushToken extends EntityPathBase<PushToken> {

    private static final long serialVersionUID = -936279676L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPushToken pushToken = new QPushToken("pushToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath token = createString("token");

    public final net.weather.user.domain.QUser user;

    public QPushToken(String variable) {
        this(PushToken.class, forVariable(variable), INITS);
    }

    public QPushToken(Path<? extends PushToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPushToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPushToken(PathMetadata metadata, PathInits inits) {
        this(PushToken.class, metadata, inits);
    }

    public QPushToken(Class<? extends PushToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new net.weather.user.domain.QUser(forProperty("user")) : null;
    }

}

