package com.example.demojpa.service;


import org.springframework.stereotype.Service;

@Service
public class EncryptedService
{
    private char[] key={'l','f','w','p','b','y','e','q','j','x','c','t','k','o','g','v','a','s','r','h','u','n','d','z','i','m'};
    private char[] alphabet={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};


    public String encrypted(String password)
    {

        char [] myCharArray = password.toCharArray ();
        char[] nw = new char[password.length()];

        for ( int i=0; i <password.length();i++)
        {
            for(int j=0;j<26;j++)
            {
                if(myCharArray[i]==alphabet[j])
                {
                    nw[i]=key[j];

                }
            }
        }
        return new String(nw);

    }



}
