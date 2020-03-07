package apis.micro.auth.server.error.handlers;

import apis.micro.auth.server.error.exceptions.AppRuntimeException;
import apis.micro.auth.server.models.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppRuntimeException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> appRuntimeException(WebRequest request, AppRuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setMessage(ex.getMessage())
                .setStatus(ex.getErrorCodes().getCode());

        return new ResponseEntity(errorResponse, ex.getErrorCodes().getStatus());
    }
}
