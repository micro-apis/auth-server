package apis.micro.auth.server.controllers;

import apis.micro.auth.server.services.JwtService;
import io.jsonwebtoken.Claims;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*
    Return type = custome class JwtResponse
    when we move to post mapping
     */

    @GetMapping("/api/jwt")
    public String generateJwt(@RequestParam String username, @RequestParam String password) {
        return jwtService.authenticateUserAndGenerateJwt(username, password);
    }

    @GetMapping(value = {"/.well-known/jwks","/.well-known/jwks.json"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public JSONObject getKey(HttpServletRequest request, HttpServletResponse response) {
        logger.info("JWKS being accessed!!!");
        return getJwksJsonObject();
    }

    @GetMapping("/api/validate/jwt")
    public Claims validateAndGetClaims(@RequestHeader String jwt) {
        return jwtService.extractMyClaims(jwt);
    }
}
