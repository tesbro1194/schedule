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
    private int commentsCount;
    private String weather;

    public PlanResponseDto(Plan plan, int commentsCount) {
        this.username = plan.getUser().getUsername();
        this.title = plan.getTitle();
        this.contents = plan.getContents();
        this.modifiedAt = plan.getModifiedAt();
        this.weather = plan.getWeather();
        this.commentsCount = commentsCount;
    }
}
