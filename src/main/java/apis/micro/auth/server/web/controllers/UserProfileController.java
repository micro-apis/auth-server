package apis.micro.auth.server.web.controllers;

import apis.micro.auth.server.documents.UserProfile;
import apis.micro.auth.server.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PostMapping("/api/user-profile")
    public Mono<UserProfile> createUserProfile(@RequestParam String username) {
        return userProfileService.createUserProfile(username);
    }
}
