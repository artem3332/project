package com.example.demojpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ControllerExceptionHandler
{
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(BusinessException e)
    {
        ErrorResponse.Error error = new ErrorResponse.Error(e.getErrorCode(), e.getErrorCode().getMessage());
        ErrorResponse errorResponse = new ErrorResponse(error);
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidErrorResponse> handleException(MethodArgumentNotValidException ex)
    {

        final List<InvalidErrorResponse.InvalidError> invalidErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new InvalidErrorResponse.InvalidError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        InvalidErrorResponse invalidErrorResponse= new InvalidErrorResponse(invalidErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidErrorResponse);
    }

}
