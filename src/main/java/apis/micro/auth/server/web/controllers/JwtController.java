package apis.micro.auth.server.web.controllers;

import apis.micro.auth.server.models.JwtResponse;
import apis.micro.auth.server.models.JwtAuthenticationRequest;
import apis.micro.auth.server.services.JwtService;
import io.jsonwebtoken.Claims;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static apis.micro.auth.server.utils.KeyStoreUtil.getJwksJsonObject;

@RestController
public class JwtController {

    private final Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/jwt")
    public JwtResponse generateJwt(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest) {
        String token = jwtService.authenticateUserAndGenerateJwt(jwtAuthenticationRequest);
        return generateJwtResponse(token, jwtAuthenticationRequest.getUserName());
    }

    private JwtResponse generateJwtResponse(String token, String username) {
        return new JwtResponse()
                .setToken(token)
                .setUsername(username)
                .setCode(HttpStatus.OK.value());
    }

    @GetMapping(value = {"/.well-known/jwks","/.well-known/jwks.json"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public JSONObject getKey(HttpServletRequest request, HttpServletResponse response) {
        logger.info("JWKS being accessed!!!");
        return getJwksJsonObject();
    }

    @GetMapping("/api/validate/jwt")
    public Claims validateAndGetClaims(@RequestHeader String jwt) {
        return jwtService.validateJwtTokenAndGetClaims(jwt);
    }
}
