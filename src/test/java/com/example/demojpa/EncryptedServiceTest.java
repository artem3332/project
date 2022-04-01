package com.example.demojpa;

import com.example.demojpa.service.EncryptedService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptedServiceTest {

    @Autowired
    private EncryptedService encryptedService;

    @Test
    public void encrypted(){
        String password=encryptedService.encrypted("password");
        Assert.assertTrue(password!=null);


    }

}