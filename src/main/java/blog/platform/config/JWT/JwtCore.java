package blog.platform.config.JWT;

import blog.platform.config.UserDetailsImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtCore {
    @Value("${blog.platform.secret}")
    private String secret;

    @Value("${blog.platform.lifeTime}")
    private Long lifeTime;

    public String generateToken(UserDetailsImpl userDetails) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("user",userDetails.getUsername());
        claims.put("password",userDetails.getPassword());
        claims.put("email",userDetails.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+lifeTime))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimFromToken(token).getSubject();
    }


    private Claims getAllClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
