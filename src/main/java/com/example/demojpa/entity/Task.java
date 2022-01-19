package com.example.demojpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String task;
    private boolean readiness;

    @Column(name = "user_id")
    private Long userId;

    private Status status;
    private LocalDateTime time;

    public Task(String task, boolean readiness)
    {
        this.task = task;
        this.readiness = readiness;
    }

    public enum Status
    {
        COMPLETED,
        PROCESS,
        FAILED

    }

}
