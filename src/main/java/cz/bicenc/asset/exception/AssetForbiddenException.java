package cz.bicenc.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AssetForbiddenException extends AssetException {

    public AssetForbiddenException() {
    }

    public AssetForbiddenException(String message) {
        super(message);
    }

    public AssetForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetForbiddenException(Throwable cause) {
        super(cause);
    }

    public AssetForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
