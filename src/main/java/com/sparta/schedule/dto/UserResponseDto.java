package com.sparta.schedule.dto;

import com.sparta.schedule.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String email;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
