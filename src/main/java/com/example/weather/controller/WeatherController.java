package com.example.weather.controller;

import com.example.weather.entity.WeatherEntity;
import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherService weatherService;


    @PostMapping("/fetch")
    public ResponseEntity<WeatherEntity> fetchWeather() {
        try {
            log.info("POST /api/weather/fetch - Fetching weather data");
            WeatherEntity weather = weatherService.fetchAndSaveWeather();
            return ResponseEntity.ok(weather);
        } catch (Exception e) {
            log.error("Error in fetchWeather endpoint", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/fetch/{city}")
    public ResponseEntity<WeatherEntity> fetchWeatherForCity(@PathVariable String city) {
        try {
            log.info("POST /api/weather/fetch/{} - Fetching weather data for city", city);
            WeatherEntity weather = weatherService.fetchAndSaveWeatherForCity(city);
            return ResponseEntity.ok(weather);
        } catch (Exception e) {
            log.error("Error in fetchWeatherForCity endpoint for city: {}", city, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/records")
    public ResponseEntity<List<WeatherEntity>> getAllWeatherRecords() {
        try {
            log.info("GET /api/weather/records - Getting all weather records");
            List<WeatherEntity> records = weatherService.getAllWeatherRecords();
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            log.error("Error in getAllWeatherRecords endpoint", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/records/{city}")
    public ResponseEntity<List<WeatherEntity>> getWeatherRecordsByCity(@PathVariable String city) {
        try {
            log.info("GET /api/weather/records/{} - Getting weather records for city", city);
            List<WeatherEntity> records = weatherService.getWeatherRecordsByCity(city);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            log.error("Error in getWeatherRecordsByCity endpoint for city: {}", city, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}