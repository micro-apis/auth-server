package apis.micro.auth.server.web.interceptors;

import apis.micro.auth.server.error.ErrorCodes;
import apis.micro.auth.server.error.exceptions.AppRuntimeException;
import apis.micro.auth.server.services.JwtService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isEmpty(jwtToken)) {
            String message = "Missing request header " + HttpHeaders.AUTHORIZATION;
            logger.error("Error:{} ", message);
            throw new AppRuntimeException(message, ErrorCodes.BAD_REQUEST);
        }

        try {
            Claims claims = jwtService.validateJwtTokenAndGetClaims(jwtToken);

            //TODO claims check if issuers is null
            if(!environment.getProperty("spring.application.name").equals(claims.getIssuer())) {
                logger.error("Invalid Issuer");
                throw new AppRuntimeException("Invalid JWT token: Invalid Issuer", ErrorCodes.UNAUTHORIZED);
            }
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | ExpiredJwtException ex) {
            logger.error("Exception Message:{}", ex.getMessage());
            throw new AppRuntimeException("Invalid JWT token: " + ex.getMessage(), ErrorCodes.UNAUTHORIZED, ex);
        }
        return true;
    }

}
