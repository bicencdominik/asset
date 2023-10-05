package cz.bicenc.asset.exception;

public class AssetActionException extends AssetException {

    public AssetActionException() {
    }

    public AssetActionException(String message) {
        super(message);
    }

    public AssetActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetActionException(Throwable cause) {
        super(cause);
    }

    public AssetActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
