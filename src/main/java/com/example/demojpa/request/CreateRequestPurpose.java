package com.example.demojpa.request;

import lombok.Data;

@Data
public class CreateRequestPurpose
{

    private String purpose;
    private int importance;
    private int days;

}
