package com.sparta.schedule.entity;

import com.sparta.schedule.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public Comment(CommentRequestDto requestDto, Plan plan) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.plan = plan;
    }

    public void update(CommentRequestDto requestDto, Plan plan) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.plan = plan;
    }
}