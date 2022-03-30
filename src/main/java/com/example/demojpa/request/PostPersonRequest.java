package com.example.demojpa.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class PostPersonRequest
{

    private final String login;


    private final String password;


    @Email
    private final String email;


    private final Integer vkid;

}
