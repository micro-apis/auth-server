package apis.micro.auth.server.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "users")
public class User implements Persistable<String> {

    @Id
    private String id;

    @Field("username")
    @Indexed(unique = true)
    private String userName;

    private String password;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    private Boolean deleted;

    private Boolean enabled;

    @DBRef(db = "roles", lazy = true)
    private Role role;

    @DBRef(db = "userProfiles", lazy = true)
    private UserProfile userProfile;

    @Override
    public boolean isNew() {
        if(getCreated() == null)
            return true;
        else
            return false;
    }
}
