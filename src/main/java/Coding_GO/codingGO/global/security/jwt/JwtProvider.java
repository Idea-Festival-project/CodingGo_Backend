package Coding_GO.codingGO.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {
    @Value("${jwt.secret}") private String secretKey;
    @Value("${jwt.access-token-validity}") private long accessTokenValidity;
    @Value("${jwt.refresh-token-validity}") private long refreshTokenValidity;
    private Key key;

    @PostConstruct
    protected void init() { this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); }

    public String createAccessToken(String email) { return createToken(email, accessTokenValidity); }
    public String createRefreshToken(String email) { return createToken(email, refreshTokenValidity); }

    private String createToken(String email, long validity) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) { return false; }
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getPayload().getSubject();
    }

    public Long getExpiration(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getPayload().getExpiration().getTime();
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(getEmail(token), "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}