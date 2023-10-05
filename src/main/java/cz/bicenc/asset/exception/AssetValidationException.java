package cz.bicenc.asset.exception;

public class AssetValidationException extends AssetException {

    public AssetValidationException() {
    }

    public AssetValidationException(String message) {
        super(message);
    }

    public AssetValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetValidationException(Throwable cause) {
        super(cause);
    }

    public AssetValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
