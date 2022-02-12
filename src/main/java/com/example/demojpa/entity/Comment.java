package com.example.demojpa.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;
    private LocalDateTime dateTime;

    @Column(name = "purpose_id")
    private Long purposeId;




    public Comment(String comment, LocalDateTime dateTime) {
        this.comment = comment;
        this.dateTime = dateTime;
    }
}
