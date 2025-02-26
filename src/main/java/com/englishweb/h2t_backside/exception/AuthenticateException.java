package com.englishweb.h2t_backside.exception;

import java.io.Serial;

public class AuthenticateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String errorDetail;

    public AuthenticateException(String message) {
        super(message);
        this.errorDetail = null;
    }

    public AuthenticateException(String message, Throwable cause) {
        super(message, cause);
        this.errorDetail = cause.getMessage();
    }

    public String getErrorDetail() {
        return errorDetail;
    }
}
