package com.example.demojpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String login;
    private String password;

    private Integer vkid;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Purpose> purposes;

    private String email;



    public Person(String login, String parol,String email,Integer vkid)
    {
        this.login = login;
        this.password = parol;
        this.email=email;
        this.vkid =vkid;
    }

}
