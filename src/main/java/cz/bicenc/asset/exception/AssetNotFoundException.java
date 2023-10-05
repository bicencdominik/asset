package cz.bicenc.asset.exception;

public class AssetNotFoundException extends AssetException {

    public AssetNotFoundException() {
    }

    public AssetNotFoundException(String message) {
        super(message);
    }

    public AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetNotFoundException(Throwable cause) {
        super(cause);
    }

    public AssetNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
