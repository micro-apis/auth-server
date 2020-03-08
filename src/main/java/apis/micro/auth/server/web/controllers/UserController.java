package apis.micro.auth.server.web.controllers;

import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.models.UserRequest;
import apis.micro.auth.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/public/user")
    public Mono<User> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

}
