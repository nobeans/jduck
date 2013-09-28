package org.coderepos.lang.java.jduck.exception;

public class MethodMissingRuntimeException extends RuntimeException {

    public MethodMissingRuntimeException() {
        super();
    }

    public MethodMissingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodMissingRuntimeException(String message) {
        super(message);
    }

    public MethodMissingRuntimeException(Throwable cause) {
        super(cause);
    }

}
