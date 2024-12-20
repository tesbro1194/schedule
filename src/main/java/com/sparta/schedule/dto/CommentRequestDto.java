package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @Size(min =2, max = 10)
    private String username;
    @Size(min =2, max = 100)
    private String contents;
    @NotNull
    private Long planId;
}