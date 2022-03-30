package com.example.demojpa.response;



import com.example.demojpa.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindNotificationResponse
{


    private final String notification;
    private final Status status;
    private final LocalDateTime time;

}
