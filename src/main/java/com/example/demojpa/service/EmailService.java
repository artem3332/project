package com.example.demojpa.service;

public interface EmailService
{
    void sendSimpleEmail(String toAddress, String subject, String message);
}
