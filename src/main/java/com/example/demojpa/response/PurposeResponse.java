package com.example.demojpa.response;



import com.example.demojpa.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurposeResponse
{


    private final String purpose;
    private final Status status;
    private final LocalDateTime time;

}
