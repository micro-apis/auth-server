package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.User;
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

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.JwsHeader.KEY_ID;

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
        if (userMono.blockOptional().isPresent()) {
            //TODO if matches then generate else raise Exception and return 401
            passwordEncoder.matches(password, userMono.block().getPassword());
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

    // TODO catch all JwtParser Runtime exceptions
    public Claims extractMyClaims(String token) {
        return Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUserName());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        // Header header = Jwts.jwsHeader().setKeyId("default-key-id");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(KEY_ID, "default-key-id");
        headerMap.put(TYPE, JWT_TYPE);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.RS256, keyPair.getPrivate())
                //.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();  // This is for Symmetric KeyPair
                .setHeader(headerMap)
                .compact();
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
    }
}