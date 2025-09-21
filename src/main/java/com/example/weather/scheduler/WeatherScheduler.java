package com.example.weather.scheduler;

import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherScheduler {
    private static final Logger logger = LoggerFactory.getLogger(WeatherScheduler.class);

    private final WeatherService weatherService;

    public WeatherScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @Scheduled(cron = "0 0 * * * *")
    public void fetchWeatherHourly() {
        try {
            logger.info("Scheduled weather fetch started");
            weatherService.fetchAndSaveWeather();
            logger.info("Scheduled weather fetch completed successfully");
        } catch (Exception e) {
            logger.error("Error in scheduled weather fetch", e);
        }
    }
}