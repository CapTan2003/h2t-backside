package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Getter;

import java.io.Serial;

@Getter
public class AuthenticateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String errorDetail;
    private final SeverityEnum severityEnum;

    public AuthenticateException(String message, SeverityEnum severityEnum) {
        super(message);
        this.severityEnum = severityEnum;
        this.errorDetail = null;
    }

    public AuthenticateException(String message, Throwable cause, SeverityEnum severityEnum) {
        super(message, cause);
        this.errorDetail = cause.getMessage();
        this.severityEnum = severityEnum;
    }

}
