package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String username;
    private String contents;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUsername();
        this.contents = comment.getContents();
        this.modifiedAt = comment.getModifiedAt();
    }
}
