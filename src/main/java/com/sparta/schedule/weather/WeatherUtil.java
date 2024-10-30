package com.sparta.schedule.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.dto.WeatherDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class WeatherUtil {

    private final RestTemplate restTemplate;

    public WeatherUtil(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getWeather() throws JsonProcessingException {
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

        return todayWeather;
    }
}
