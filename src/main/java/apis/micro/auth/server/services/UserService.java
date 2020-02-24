package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.Role;
import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.documents.UserProfile;
import apis.micro.auth.server.repositories.RoleRepository;
import apis.micro.auth.server.repositories.UserProfileRepository;
import apis.micro.auth.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Mono<User> createUser() {
        Role role = new Role()
                .setRoleName("user")
                .setDeleted(false)
                .setEnabled(true);
        Mono<Role> roleMono = roleRepository.save(role);

        UserProfile userProfile = new UserProfile()
                .setFirstName("Diway")
                .setLastName("Sanu")
                .setGender("Male")
                .setDeleted(false);
        Mono<UserProfile> userProfileMono = userProfileRepository.save(userProfile);

        User user = new User();
        user.setUserName("diway@gmail.com")
                .setPassword(passwordEncoder.encode("Test1234"))
                .setDeleted(false)
                .setEnabled(true)
                .setRole(roleMono.block())
                .setUserProfile(userProfileMono.block());

        Mono<User> userMono = userRepository.save(user);

        return userMono;
    }
}
