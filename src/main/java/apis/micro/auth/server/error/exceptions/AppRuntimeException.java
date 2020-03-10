package apis.micro.auth.server.error.exceptions;

import apis.micro.auth.server.error.ErrorCodes;
import lombok.Getter;
import lombok.Setter;


public class AppRuntimeException extends RuntimeException {

    @Getter
    private ErrorCodes errorCodes = ErrorCodes.INTERNAL_SERVER_ERROR;

    @Getter
    private Throwable exception;

    public AppRuntimeException(String errorMsg, ErrorCodes errorCodes, Throwable ex) {
        super(errorMsg, ex);
        this.errorCodes = errorCodes;
        this.exception = ex;
    }

    public AppRuntimeException(String errorMsg, Throwable ex) {
        super(errorMsg, ex);
    }

    public AppRuntimeException(String errorMsg, ErrorCodes errorCodes) {
        super(errorMsg);
        this.errorCodes = errorCodes;
    }
}
