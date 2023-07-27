package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * Helper class to handle JSON Web Tokens (JWT).
 */
public class JwtHelper {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Parses a JWT and validates its signature.
     *
     * @param jwt the JWT to parse.
     * @return the parsed JWT.
     */
    public static Jws<Claims> parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt);
    }

    /**
     * Generates a JSON Web Token (JWT) for the given user. The generated token has a fixed
     * expiry time of 30 minutes from the current system time.
     *
     * @param user the User entity for which the JWT is to be generated. The username of the user is set as the subject of the JWT.
     * @return a string representing the signed JWT.
     */
    public static String generateJwt(User user) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1800000; // 30 minutes in milliseconds
        Date exp = new Date(expMillis);

        String jws = Jwts.builder()
                .setSubject(user.getUserName())
                .setExpiration(exp)
                .signWith(SECRET_KEY)
                .compact();
        return jws;
    }
}
