package net.weather.lol.summoner.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSummoner is a Querydsl query type for Summoner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSummoner extends EntityPathBase<Summoner> {

    private static final long serialVersionUID = -868979928L;

    public static final QSummoner summoner = new QSummoner("summoner");

    public final StringPath accountId = createString("accountId");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath puuid = createString("puuid");

    public final DateTimePath<java.time.Instant> revisionDate = createDateTime("revisionDate", java.time.Instant.class);

    public QSummoner(String variable) {
        super(Summoner.class, forVariable(variable));
    }

    public QSummoner(Path<? extends Summoner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSummoner(PathMetadata metadata) {
        super(Summoner.class, metadata);
    }

}

