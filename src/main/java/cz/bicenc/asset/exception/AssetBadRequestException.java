package cz.bicenc.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AssetBadRequestException extends AssetException {
    public AssetBadRequestException() {
    }

    public AssetBadRequestException(String message) {
        super(message);
    }

    public AssetBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetBadRequestException(Throwable cause) {
        super(cause);
    }

    public AssetBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
