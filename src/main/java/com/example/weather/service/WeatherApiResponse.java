package com.example.weather.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherApiResponse(Main main, List<Weather> weather, String name) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(Double temp, Double feels_like, Double temp_min, Double temp_max, Integer pressure, Integer humidity) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(String main, String description, String icon) {}
}