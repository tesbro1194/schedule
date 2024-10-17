package com.sparta.schedule.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.schedule.dto.PlanRequestDto;
import com.sparta.schedule.dto.PlanResponseDto;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    @PostMapping("/create")
    @ResponseBody
    public PlanResponseDto createPlan (@RequestBody PlanRequestDto requestDto) throws JsonProcessingException {
        return planService.createPlan(requestDto);
    }

    @GetMapping("/get-all")
    @ResponseBody
    public Page<PlanResponseDto> getAll (@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "sortBy",defaultValue = "modifiedAt") String sortBy,
                                         @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        return planService.getAll(page, size, sortBy, isAsc);
    }

    @PatchMapping("/update/{planId}")
    public PlanResponseDto updatePlan (@PathVariable Long planId, @RequestBody PlanRequestDto requestDto) {
        return planService.update(planId, requestDto);
    }

    @DeleteMapping("/delete/{planId}")
    public String delete(@PathVariable Long planId) {
        planService.delete(planId);
        return "redirect:/plan/get-all";
    }
}
