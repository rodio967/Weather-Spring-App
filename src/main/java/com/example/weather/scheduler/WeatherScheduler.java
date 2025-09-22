package com.example.weather.scheduler;

import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherScheduler {
    private final WeatherService weatherService;

    @Scheduled(cron = "0 0 * * * *")
    public void fetchWeatherHourly() {
        try {
            log.info("Scheduled weather fetch started");
            weatherService.fetchAndSaveWeather();
            log.info("Scheduled weather fetch completed successfully");
        } catch (Exception e) {
            log.error("Error in scheduled weather fetch", e);
        }
    }
}