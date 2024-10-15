package com.sparta.schedule.service;

import com.sparta.schedule.dto.PlanRequestDto;
import com.sparta.schedule.dto.PlanResponseDto;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public void createPlan(PlanRequestDto requestDto) {
        planRepository.save(new Plan(requestDto));
    }

    public Page<PlanResponseDto> getAll(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Plan> planList;
        planList = planRepository.findAll(pageable);

        return planList.map(plan -> new PlanResponseDto(plan, plan.getCommentList().size()));
    }

    @Transactional
    public void update(Long id, PlanRequestDto requestDto) {
        Plan plan = findPlanById(id);
        plan.update(requestDto);
    }

    public void delete(Long id) {
        Plan plan = findPlanById(id);
        planRepository.deleteById(plan.getId());
    }
    private Plan findPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정는 존재하지 않습니다.")
        );
    }
}
