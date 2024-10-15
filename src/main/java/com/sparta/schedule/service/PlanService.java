package com.sparta.schedule.service;

import com.sparta.schedule.dto.PlanRequestDto;
import com.sparta.schedule.dto.PlanResponseDto;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
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

    public List<PlanResponseDto> getAll() {
        List<Plan> planList = planRepository.findAll();

        List<PlanResponseDto> responseDtoList = new ArrayList<>();

        for (Plan plan : planList) {
            responseDtoList.add(new PlanResponseDto(plan));
        }

        return responseDtoList;
    }

    @Transactional
    public void update(Long id, PlanRequestDto requestDto) {
        Plan plan = findPlan(id);
        plan.update(requestDto);
    }

    public void delete(Long id) {
        Plan plan = findPlan(id);
        planRepository.deleteById(plan.getId());
    }

    private Plan findPlan(Long id) {
        return planRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
