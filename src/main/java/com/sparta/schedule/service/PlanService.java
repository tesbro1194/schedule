package com.sparta.schedule.service;

import com.sparta.schedule.dto.PlanRequestDto;
import com.sparta.schedule.dto.PlanResponseDto;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.PlanRepository;
import com.sparta.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public void createPlan(PlanRequestDto requestDto) {
        User user = findUserById(requestDto.getUserId());
        planRepository.save(new Plan(requestDto, user));
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
        User user = findUserById(requestDto.getUserId());
        plan.update(requestDto, user);
    }

    public void delete(Long id) {
        Plan plan = findPlanById(id);
        planRepository.deleteById(plan.getId());
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정는 존재하지 않습니다.")
        );
    }

    private Plan findPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정는 존재하지 않습니다.")
        );
    }
}
