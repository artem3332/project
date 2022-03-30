package com.example.demojpa.request;

import lombok.Data;

@Data
public class DeleteNotificationRequest {

    private final String notification;
    private final Long userId;
}
