package apis.micro.auth.server.repositories;


import apis.micro.auth.server.documents.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

}
