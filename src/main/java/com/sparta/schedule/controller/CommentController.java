package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{planId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public List<CommentResponseDto> createComment (@PathVariable Long planId,
                                                   @RequestBody CommentRequestDto requestDto) {
        commentService.createComment(planId, requestDto);
        return commentService.getAll(planId);
    }

    @GetMapping("/getAll")
    public List<CommentResponseDto> getAll (@PathVariable Long planId) {
        return commentService.getAll(planId);
    }

    @PatchMapping("/update/{id}")
    public List<CommentResponseDto> update (@PathVariable Long planId, @PathVariable Long id,
                                            @RequestBody CommentRequestDto requestDto) {
        commentService.update(id, requestDto);
        return commentService.getAll(planId);
    }

    @DeleteMapping("/delete/{id}")
    public List<CommentResponseDto> delete(@PathVariable Long planId, @PathVariable Long id) {
        commentService.delete(id);
        return commentService.getAll(planId);
    }
}
