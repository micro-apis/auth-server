package apis.micro.auth.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class JwtAuthenticationRequest {

    @JsonProperty("username")
    private String userName;
    private String password;
    private Boolean client = Boolean.FALSE;

}
