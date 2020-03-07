package apis.micro.auth.server.error;

import apis.micro.auth.server.models.constants.SystemType;
import org.springframework.http.HttpStatus;

public enum ErrorCodes {

    BAD_REQUEST(400, HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED),
    NOT_FOUND(404, HttpStatus.NOT_FOUND),
    UNPROCESSABLE_ENTITY(422, HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private HttpStatus status;
    private SystemType systemType = SystemType.AUTH_SVC;

    ErrorCodes(int code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    ErrorCodes(int code, HttpStatus status, SystemType systemType) {
        this.code = status.value();
        this.status = status;
        this.systemType = systemType;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public SystemType getSystemType() {
        return systemType;
    }
}
