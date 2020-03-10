package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.Role;
import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.documents.UserProfile;
import apis.micro.auth.server.error.ErrorCodes;
import apis.micro.auth.server.error.exceptions.AppRuntimeException;
import apis.micro.auth.server.models.UserRequest;
import apis.micro.auth.server.repositories.RoleRepository;
import apis.micro.auth.server.repositories.UserProfileRepository;
import apis.micro.auth.server.repositories.UserRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService extends BaseService<User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Mono<User> createUser(final UserRequest userRequest) {

        Mono<Role> roleMono = roleRepository.findByRoleName("USER");

        User user = new User();
        user.setUserName(userRequest.getUserName())
                .setPassword(passwordEncoder.encode(userRequest.getPassword()))
                .setDeleted(false)
                .setEnabled(true)
                .setRole(roleMono.block());

        Mono<User> userMono = userRepository.save(user);
        //TODO Mono.just()
        return userMono.onErrorResume(handleMongoDbMonoErrors);
    }

    public Mono<User> getUser(String username) {

        return userRepository.findByUserName(username);
    }
}
