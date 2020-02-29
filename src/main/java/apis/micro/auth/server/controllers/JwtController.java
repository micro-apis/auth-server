package apis.micro.auth.server.controllers;

import apis.micro.auth.server.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

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
}
