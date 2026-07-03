package learning.springboot.JpaMapping.exception;

import lombok.Getter;

public abstract class ApplicationException extends RuntimeException {

    private final String message;
    @Getter
    private final String errorCode;

    protected ApplicationException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}