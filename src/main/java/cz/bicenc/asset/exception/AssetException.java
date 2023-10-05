package cz.bicenc.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AssetException extends RuntimeException {

    public AssetException() {
    }

    public AssetException(String message) {
        super(message);
    }

    public AssetException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetException(Throwable cause) {
        super(cause);
    }

    public AssetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
