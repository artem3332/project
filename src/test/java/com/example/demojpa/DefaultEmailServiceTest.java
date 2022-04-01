package com.example.demojpa;


import com.example.demojpa.service.DefaultEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultEmailServiceTest {

    @Autowired
    private DefaultEmailService defaultEmailService;

    @MockBean
    private JavaMailSender emailSender;

    @Test
    public void sendSimpleEmail(){
        defaultEmailService.sendSimpleEmail("dd.prof@mail.ru","hello","WhatsApp?");
        Mockito.verify(emailSender,Mockito.times(1)).send(ArgumentMatchers.any(SimpleMailMessage.class));


    }


}