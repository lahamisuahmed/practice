package com.practice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {

    private String status;
    private String message;
    private String info;

    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> fieldErrors = new HashMap<>();

    public void addError(String field, String message){
        fieldErrors.put(field, message);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, Object> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
