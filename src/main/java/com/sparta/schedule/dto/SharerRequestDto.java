package com.sparta.schedule.dto;

import com.sparta.schedule.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SharerRequestDto {
    @NotNull
    private List<User> userList;
    @NotNull
    private Long planId;
}
