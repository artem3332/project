package com.example.demojpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String notification;

    @Column(name = "user_id")
    private Long userId;

    @OrderBy("status, importance ASC")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<Notification> subGoals;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private LocalDateTime time;

    private int importance;

    private String email;

    public Notification(String notification,Status status,LocalDateTime time)
    {

        this.notification = notification;
        this.status=status;
        this.time=time;
    }

}
