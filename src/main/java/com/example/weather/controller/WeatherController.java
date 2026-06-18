package com.example.weather.controller;

import com.example.weather.repository.WeatherEntity;
import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;


    @PostMapping("/fetch")
    public ResponseEntity<WeatherEntity> fetchWeather() {
        log.info("POST /api/weather/fetch - Fetching weather data");
        WeatherEntity weather = weatherService.fetchAndSaveWeather();
        return ResponseEntity.ok(weather);
    }

    @PostMapping("/fetch/{city}")
    public ResponseEntity<WeatherEntity> fetchWeatherForCity(@PathVariable String city) {
        log.info("POST /api/weather/fetch/{} - Fetching weather data for city", city);
        WeatherEntity weather = weatherService.fetchAndSaveWeatherForCity(city);
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/records")
    public ResponseEntity<List<WeatherEntity>> getAllWeatherRecords() {
        log.info("GET /api/weather/records - Getting all weather records");
        List<WeatherEntity> records = weatherService.getAllWeatherRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/records/{city}")
    public ResponseEntity<List<WeatherEntity>> getWeatherRecordsByCity(@PathVariable String city) {
        log.info("GET /api/weather/records/{} - Getting weather records for city", city);
        List<WeatherEntity> records = weatherService.getWeatherRecordsByCity(city);
        return ResponseEntity.ok(records);
    }
}