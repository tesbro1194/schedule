package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String username;
    private String contents;
    private Plan plan;
}
