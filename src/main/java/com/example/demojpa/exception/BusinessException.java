package com.example.demojpa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException
{
    ErrorCode errorCode;
    HttpStatus httpStatus;


}
