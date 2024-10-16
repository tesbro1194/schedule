package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDto {
    @Size(min =2, max = 20)
    private String title;
    @NotNull
    private String contents;
    @Size(min =2, max = 10)
    private Long userId;
}