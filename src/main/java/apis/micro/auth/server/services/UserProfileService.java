package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.documents.UserProfile;
import apis.micro.auth.server.repositories.UserProfileRepository;
import apis.micro.auth.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    public Mono<UserProfile> createUserProfile(String username) {

        UserProfile userProfile = new UserProfile()
                .setFirstName("Diway")
                .setLastName("Sanu")
                .setGender("Male")
                .setDeleted(false);
        Mono<UserProfile> userProfileMono = userProfileRepository.save(userProfile);

        Mono<User> userMono = userRepository.findByUserName(username);

        userMono.block().setUserProfile(userProfileMono.block());
        userRepository.save(userMono.block());

        return userProfileMono;
    }
}
