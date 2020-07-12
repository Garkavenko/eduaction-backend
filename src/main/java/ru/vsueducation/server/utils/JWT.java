package ru.vsueducation.server.utils;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JWT {
    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256("education-front-secret-key=11223344");
    }

    public static String createJWT(final String userId) {
        try {
            Algorithm algorithm = getAlgorithm();
             return com.auth0.jwt.JWT.create()
                 .withAudience(userId)
                 .withClaim("userId", userId)
                 .sign(algorithm);
        } catch (JWTCreationException exception){
            return null;
        }
    }

    public static Integer getUserIdByToken(final String token) {
        JWTVerifier verifier = com.auth0.jwt.JWT.require(getAlgorithm())
                .acceptExpiresAt(new Date().getTime())
                .build();
        final DecodedJWT decodedJWT = verifier.verify(token);
        return Integer.parseInt(decodedJWT.getClaim("userId").asString());
    }

    public static String createJWT(final int userId) {
        return createJWT(String.valueOf(userId));
    }
}
