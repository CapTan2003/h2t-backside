package com.englishweb.h2t_backside.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Long resourceId;

    public ResourceNotFoundException(Long resourceId) {
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }

}
