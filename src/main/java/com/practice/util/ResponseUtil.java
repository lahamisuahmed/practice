package com.practice.util;

import com.practice.exception.ErrorResponse;

public class ResponseUtil {

    public static ErrorResponse buildErrorResponse(String status, String message, String info){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setInfo(info);

        return errorResponse;
    }
}
