package com.example.weather.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {
    private Long id;
    private String city;
    private Double temperature;
    private LocalDateTime timestamp;
    private String description;


    public WeatherEntity(String city, Double temperature, LocalDateTime timestamp, String description) {
        this.city = city;
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.description = description;
    }
}