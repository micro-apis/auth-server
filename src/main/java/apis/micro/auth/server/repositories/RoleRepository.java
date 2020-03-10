package apis.micro.auth.server.repositories;


import apis.micro.auth.server.documents.Role;
import apis.micro.auth.server.documents.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

    Mono<Role> findByRoleName(String roleName);
}
