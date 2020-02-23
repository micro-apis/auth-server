package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.Role;
import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.repositories.RoleRepository;
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
    private BCryptPasswordEncoder passwordEncoder;

    public Mono<User> createUser() {
        Role role = new Role()
                .setRoleName("user");
        Mono<Role> roleMono = roleRepository.save(role);

        User user = new User();
        user.setUserName("diway")
                .setPassword(passwordEncoder.encode("Test1234"))
                .setDeleted(false)
                .setEnabled(true)
                .setRole(roleMono.block());

        Mono<User> userMono = userRepository.save(user);
        return userMono;
    }
}
