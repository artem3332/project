package com.example.demojpa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse
{

    private Error error;

    @AllArgsConstructor
    @Getter
    public static class Error
    {
        ErrorCode errorCode;
        String message;

    }
}
