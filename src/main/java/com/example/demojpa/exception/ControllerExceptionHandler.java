package com.example.demojpa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler
{
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
        ErrorResponse.Error error = new ErrorResponse.Error(e.getErrorCode(), e.getErrorCode().getMessage());
        ErrorResponse errorResponse = new ErrorResponse(error);
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

}
