package apis.micro.auth.server.controllers;

import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public Mono<User> createUser() {
        return userService.createUser();
    }

}
