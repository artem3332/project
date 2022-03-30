package com.example.demojpa.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class InvalidErrorResponse
{
    private List<InvalidError> invalidError;

    @Getter
    @AllArgsConstructor
    public static class InvalidError
    {
        String name;
        String message;

    }

}
