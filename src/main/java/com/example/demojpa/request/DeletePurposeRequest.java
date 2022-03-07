package com.example.demojpa.request;

import lombok.Data;

@Data
public class DeletePurposeRequest
{
    private final String purpose;
    private final Long userId;

}
