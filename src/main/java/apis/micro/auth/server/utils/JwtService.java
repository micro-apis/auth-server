package apis.micro.auth.server.utils;

import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("defaultKeyPair")
    KeyPair keyPair;

    private String SECRET_KEY = "secret";

    public String authenticateUserAndGenerateJwt(final String username, final String password) {
        Mono<User> userMono = userService.getUser(username);
        if(userMono.blockOptional().isPresent()) {
            passwordEncoder.matches(password, userMono.block().getPassword()); //TODO if matches then generate else raise Exception and return 401
            return generateToken(userMono.block());
        }
        return null; // TODO
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUserName());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.RS256, keyPair.getPrivate()).compact();
                //.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();  // This is for Symmetric KeyPair
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
    }
}