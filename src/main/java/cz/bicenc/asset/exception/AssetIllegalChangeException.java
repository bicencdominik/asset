package cz.bicenc.asset.exception;

public class AssetIllegalChangeException extends AssetException {

    public AssetIllegalChangeException() {
    }

    public AssetIllegalChangeException(String message) {
        super(message);
    }

    public AssetIllegalChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetIllegalChangeException(Throwable cause) {
        super(cause);
    }

    public AssetIllegalChangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
