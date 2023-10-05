package cz.bicenc.asset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AssetDuplicateRuleException extends AssetException {

    public AssetDuplicateRuleException() {
    }

    public AssetDuplicateRuleException(String message) {
        super(message);
    }

    public AssetDuplicateRuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetDuplicateRuleException(Throwable cause) {
        super(cause);
    }

    public AssetDuplicateRuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
