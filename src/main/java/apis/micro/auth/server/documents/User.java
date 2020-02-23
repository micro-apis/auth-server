package apis.micro.auth.server.documents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@Accessors(chain = true)
public class User implements Persistable<String> {

    @Id
    private String id;
    private String userName;
    private String password;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    private Boolean deleted;

    private Boolean enabled;

    @DBRef(db = "Role", lazy = true)
    private Role role;

    @Override
    public boolean isNew() {
        if(getCreated() == null)
            return true;
        else
            return false;
    }
}
