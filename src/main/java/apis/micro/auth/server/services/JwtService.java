package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.error.ErrorCodes;
import apis.micro.auth.server.error.exceptions.AppRuntimeException;
import apis.micro.auth.server.models.JwtAuthenticationRequest;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment environment;

    private String SECRET_KEY = "secret";

    public String authenticateUserAndGenerateJwt(final JwtAuthenticationRequest jwtAuthenticationRequest) {
        // TODO validate if uername & password is null
        // TODO if client is true, then get ClientMono, and validate accordingly

        Mono<User> userMono = userService.getUser(jwtAuthenticationRequest.getUserName());

        if (userMono.blockOptional().isPresent()) {
            if(passwordEncoder.matches(jwtAuthenticationRequest.getPassword(), userMono.block().getPassword())) {
                return generateToken(userMono.block());
            } else {
                throw new AppRuntimeException("Invalid username/password. Please re-enter.", ErrorCodes.UNAUTHORIZED);
            }
        } else {
            throw new AppRuntimeException("User not found. Please register and try again.", ErrorCodes.NOT_FOUND);
        }
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

    public Claims validateJwtTokenAndGetClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(token).getBody();
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | ExpiredJwtException ex) {
            throw ex;
        }
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
                .setIssuer(environment.getProperty("spring.application.name"))
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