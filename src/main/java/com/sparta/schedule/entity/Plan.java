package com.sparta.schedule.entity;

import com.sparta.schedule.dto.PlanRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan")
@Getter
@NoArgsConstructor
public class Plan extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String title;

    @Column
    private String contents;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public Plan(PlanRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(PlanRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}