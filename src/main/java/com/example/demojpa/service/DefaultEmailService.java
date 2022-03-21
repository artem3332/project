package com.example.demojpa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DefaultEmailService implements EmailService
{
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message)
    {
        SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setFrom("zuvoy345@gmail.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setSentDate(new Date());
        emailSender.send(simpleMailMessage);
    }
}