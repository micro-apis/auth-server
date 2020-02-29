package apis.micro.auth.server.configs;

import apis.micro.auth.server.utils.JksUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.security.KeyPair;

@Configuration
public class JwtConfig {

    @Autowired
    Environment environment;

    @Bean(name = "defaultKeyPair")
    public KeyPair defaultKeyPair() {
        return JksUtil
                .jwtKeyPair(
                        environment.getProperty("DEFAULT_JWT_JKS_PATH"),
                        environment.getProperty("DEFAULT_JWT_JKS_PASS"),
                        environment.getProperty("DEFAULT_JWT_JKS_ALIAS"));
    }
}
