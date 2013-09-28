package org.coderepos.lang.java.jduck.exception;

public class MethodInvocationRuntimeException extends RuntimeException {

    public MethodInvocationRuntimeException() {
        super();
    }

    public MethodInvocationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodInvocationRuntimeException(String message) {
        super(message);
    }

    public MethodInvocationRuntimeException(Throwable cause) {
        super(cause);
    }

}
