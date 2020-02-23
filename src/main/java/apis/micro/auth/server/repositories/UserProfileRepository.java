package apis.micro.auth.server.repositories;


import apis.micro.auth.server.documents.UserProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile, String> {

}
