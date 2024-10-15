package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PlanRepository planRepository;
    private final CommentRepository commentRepository;
    public void createComment(Long planId, CommentRequestDto requestDto) {
        Plan plan = findPlanById(planId);
        requestDto.setPlan(plan);
        commentRepository.save(new Comment(requestDto));
    }

    public List<CommentResponseDto> getAll(Long planId) {
        List<Comment> commentList = commentRepository.findAllById(planId);

        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            responseDtoList.add(new CommentResponseDto(comment));
        }

        return responseDtoList;
    }

    @Transactional
    public void update(Long commentId, CommentRequestDto requestDto) {
        Comment comment = findCommentById(commentId);
        comment.update(requestDto);
    }


    public void delete(Long id) {
        Comment comment = findCommentById(id);
        commentRepository.deleteById(comment.getId());
    }

    private Plan findPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정는 존재하지 않습니다.")
        );
    }

    private Comment findCommentById (Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다")
        );
    }
}
