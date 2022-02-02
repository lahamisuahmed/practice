package com.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiException extends ResponseStatusException {
    private ErrorResponse errorResponse;

    public ApiException(HttpStatus status, ErrorResponse errorResponse) {
        super(status);
        this.errorResponse = errorResponse;
    }

    public ApiException(HttpStatus status) {
        super(status);
    }

    public ApiException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ApiException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

}
