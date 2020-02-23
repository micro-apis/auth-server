package apis.micro.auth.server.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    @Field("role_name")
    private String roleName;

}
