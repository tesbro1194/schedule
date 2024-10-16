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
    private String title;

    @Column
    private String contents;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Sharer> sharerList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public Plan(PlanRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(PlanRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }
}