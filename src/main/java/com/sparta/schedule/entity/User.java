package com.sparta.schedule.entity;

import com.sparta.schedule.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Sharer> sharerList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Plan> plan;

    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }

    public String update(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getUsername();
        return "유저 정보를 업데이트 했습니다";
    }
}