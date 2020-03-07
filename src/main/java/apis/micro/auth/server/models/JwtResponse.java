package apis.micro.auth.server.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class JwtResponse {

    private String token;
    private String username;
    private Integer code;

}
