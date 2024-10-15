package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlanResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime modifiedAt;

    public PlanResponseDto(Plan plan) {
        this.username = plan.getUsername();
        this.title = plan.getTitle();
        this.contents = plan.getContents();
        this.modifiedAt = plan.getModifiedAt();
    }
}