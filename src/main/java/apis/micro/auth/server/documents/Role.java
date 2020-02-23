package apis.micro.auth.server.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Getter
@Setter
@Accessors(chain = true)
public class Role {

    @Id
    private String id;

    @Field("role_name")
    private String roleName;

}
