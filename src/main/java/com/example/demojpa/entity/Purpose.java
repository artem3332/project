package com.example.demojpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Purpose
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String purpose;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "purpose_id")
    private List<Comment> comments;

    @OrderBy("status, importance ASC") // sort by status and importance ASC
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<Purpose> subGoals;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private LocalDateTime time;

    private int importance;

    private int days;

    private String email;

    public Purpose(String Purpose,int importance,int days)
    {
        this.purpose = Purpose;
        this.importance=importance;
    }

    public enum Status
    {
        COMPLETED,
        FAILED,
        PROCESS
    }

}
