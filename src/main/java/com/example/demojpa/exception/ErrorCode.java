package com.example.demojpa.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode
{
    PERSON_ALREADY_EXISTS("Person already exists"),
    PERSON_NOT_FOUND("Person not found"),
    PURPOSE_ALREADY_EXISTS("Person already exists"),
    PURPOSE_NOT_FOUND("Notification not found"),
    SUBGOAL_ALREADY_EXISTS("Subgoal already exists"),
    SUBGOAL_NOT_FOUND("Subgoal not found");

    final String message;

    public String getMessage() {
        return message;
    }
}
