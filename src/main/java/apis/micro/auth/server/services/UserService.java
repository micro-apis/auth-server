package apis.micro.auth.server.services;

import apis.micro.auth.server.documents.User;
import apis.micro.auth.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> createUser() {
        User user = new User();
        user.setUserName("diway")
                .setPassword("Test")
                .setDeleted(false)
                .setEnabled(true);

        Mono<User> userMono = userRepository.save(user);
        return userMono;
    }
}
