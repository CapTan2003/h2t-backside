package com.englishweb.h2t_backside.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class InvalidArgumentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Object data;
    private final String errorCode;

    public InvalidArgumentException(String message, Object data, String errorCode) {
        super(message);
        this.data = data;
        this.errorCode = errorCode;
    }
}
