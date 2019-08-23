package kiwi.blog.common.config.authentication;

import io.jsonwebtoken.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.oauth.jwt.public-key}")
    private String jwtSecret;

    @Value("${app.oauth.jwt.expirationInMs}")
    private int jwtExpirationInMs;

    public HashMap getJwtTokenByClientCredentialForUser(OAuth2Authentication auth) throws IOException {
        Jwt jwt = JwtHelper.decode(((OAuth2AuthenticationDetails) auth.getDetails()).getTokenValue());
        String jwtClaims = jwt.getClaims();
        return new ObjectMapper().readValue(jwtClaims, HashMap.class);
    }

    public String generateToken(Authentication authentication) {

        AppUserPrincipal userPrincipal = (AppUserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}