package com.sparta.schedule.dto;

import com.sparta.schedule.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDto {
    private String title;
    private String contents;
    private User user;
}