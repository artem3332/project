package com.example.demojpa.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InvalidErrorCode
{

    LOGIN_NULL_MEANING("Login null meaning"),
    PASSWORD_NULL_MEANING("Password null meaning"),
    EMAIL_NULL_MEANING("Email null meaning"),
    EMAIL_NULL_INCORRECT("Email null minccorect");


    final String message;

    public String getMessage() {
        return message;
    }


}