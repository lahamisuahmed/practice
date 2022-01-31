package com.practice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(String.valueOf(HttpStatus.NOT_FOUND.value()));
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(String.valueOf(status.value()));
        errorResponse.setMessage(status.name());
        errorResponse.setInfo(status.getReasonPhrase());

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errorResponse.addError(fieldName, errorMessage);
        });

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorResponse> handleRestApplicationException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(String.valueOf(ex.getStatus().value()));
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

}
