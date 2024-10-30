package com.taskQuito.client_service.application.exceptions;

import org.springframework.http.HttpStatus;

public class HttpGenericException extends RuntimeException {
    private final HttpStatus status;

    public HttpGenericException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpGenericException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}