package net.weather.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.util.UUID;


public class JwtBuilder {

    public static String getDefaultToken(String secretKey){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant issuedAt = Instant.now();
        String jwt = JWT.create()
                .withIssuer("Lotification")
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plusSeconds(60L * 360L))
                .withClaim("userId", UUID.randomUUID().toString())
                .sign(algorithm);
        DecodedJWT decode = JWT.decode(jwt);

        decode.getHeader();
        decode.getPayload();
        decode.getSignature();

        return jwt;
    }
}
