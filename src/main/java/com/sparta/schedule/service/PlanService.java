package com.sparta.schedule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.dto.PlanRequestDto;
import com.sparta.schedule.dto.PlanResponseDto;
import com.sparta.schedule.dto.WeatherDto;
import com.sparta.schedule.entity.Plan;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.PlanRepository;
import com.sparta.schedule.repository.UserRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public PlanService(PlanRepository planRepository, UserRepository userRepository, RestTemplateBuilder builder) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.restTemplate = builder.build();
    }

    public PlanResponseDto createPlan(PlanRequestDto requestDto) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://f-api.github.io")
                .path("/f-api/weather.json")
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        List<WeatherDto> weatherList;

        weatherList = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<WeatherDto>>() {});

        LocalDate today = LocalDate.now();
        String todayString = today.format(DateTimeFormatter.ofPattern("MM-dd"));
        String todayWeather = weatherList.stream()
                .filter(w -> w.getDate().equals(todayString))
                .map(WeatherDto::getWeather)
                .findFirst()
                .orElse("날씨 정보 없음");

        User user = findUserById(requestDto.getUserId());
        Plan plan = planRepository.save(new Plan(requestDto, user, todayWeather));
        return new PlanResponseDto(plan, plan.getCommentList().size());
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
    public PlanResponseDto update(Long id, PlanRequestDto requestDto) {
        Plan plan = findPlanById(id);
        User user = findUserById(requestDto.getUserId());
        plan.update(requestDto, user);
        return new PlanResponseDto(plan, plan.getCommentList().size());
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
