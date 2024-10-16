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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Sharer> sharerList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Plan> plan;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String update(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getUsername();
        return "유저 정보를 업데이트 했습니다";
    }
}