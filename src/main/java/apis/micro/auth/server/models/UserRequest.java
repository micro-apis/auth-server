package apis.micro.auth.server.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserRequest {
    private String userName;
    private String password;
}
