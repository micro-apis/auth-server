package apis.micro.auth.server.services;

import apis.micro.auth.server.error.ErrorCodes;
import apis.micro.auth.server.error.exceptions.AppRuntimeException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public abstract class BaseService<T> {

    // TODO improve generics
    protected Function<? super Throwable, ? extends Mono<? extends T>> handleMongoDbMonoErrors = ex -> {
        if(ex instanceof DuplicateKeyException) {
            return Mono.error(new AppRuntimeException(ex.getMessage(), ErrorCodes.CONFLICT, ex));
        }
        return Mono.error(new AppRuntimeException(ex.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR, ex));
    };
}
