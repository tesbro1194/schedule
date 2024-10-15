package com.sparta.schedule.controller;

import com.sparta.schedule.dto.PlanRequestDto;
import com.sparta.schedule.dto.PlanResponseDto;
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
    public String createPlan (@RequestBody PlanRequestDto requestDto) {
        planService.createPlan(requestDto);
        return "redirect:/plan/get-all";
    }

    @GetMapping("/get-all")
    @ResponseBody
    public Page<PlanResponseDto> getAll (@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "sortBy",defaultValue = "modifiedAt") String sortBy,
                                         @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        return planService.getAll(page, size, sortBy, isAsc);
    }

    @PatchMapping("/update/{id}")
    public String updatePlan (@PathVariable Long id, @RequestBody PlanRequestDto requestDto) {
        planService.update(id, requestDto);
        return "redirect:/plan/get-all";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        planService.delete(id);
        return "redirect:/plan/get-all";
    }
}
