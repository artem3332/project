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
    private String parol;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Purpose> purposes;

    private String email;



    public Person(String login, String parol,String email)
    {
        this.login = login;
        this.parol = parol;
        this.email=email;
    }

}
