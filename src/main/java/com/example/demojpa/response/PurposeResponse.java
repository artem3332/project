package com.example.demojpa.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurposeResponse
{
    private final String purpose;
    private final LocalDateTime time;

}
