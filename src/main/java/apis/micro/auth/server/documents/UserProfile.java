package apis.micro.auth.server.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "user_profile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile implements Persistable {

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private String gender;

    @CreatedDate
    @JsonFormat(pattern= "yyyy-MM-dd")
    private LocalDateTime created;

    @LastModifiedDate
    @JsonFormat(pattern= "yyyy-MM-dd")
    private LocalDateTime updated;

    private Boolean deleted;

    @Override
    @JsonIgnore
    public boolean isNew() {
        if(getCreated() == null)
            return true;
        else
            return false;
    }
}
