package com.practice.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, options);

        String status = defaultErrorAttributes.get("status").toString();
        String message = HttpStatus.valueOf(Integer.parseInt(status)).name();
        String info = defaultErrorAttributes.get("message").toString();
        if (info.isEmpty()) {
            if (HttpStatus.INTERNAL_SERVER_ERROR.compareTo(HttpStatus.valueOf(Integer.parseInt(status))) == 0) {
                info = "Error processing your request. Please try again.";
            } else if (HttpStatus.NOT_FOUND.compareTo(HttpStatus.valueOf(Integer.parseInt(status))) == 0) {
                info = "Resource not found.";
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("info", info);

        return response;
    }

}
