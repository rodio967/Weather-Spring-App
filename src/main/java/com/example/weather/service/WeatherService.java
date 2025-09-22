package com.example.weather.service;

import com.example.weather.dto.WeatherApiResponse;
import com.example.weather.entity.WeatherEntity;
import com.example.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final WebClient webClient;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.units}")
    private String units;

    @Value("${weather.api.lang}")
    private String lang;

    @Value("${weather.city}")
    private String defaultCity;


    private WeatherApiResponse fetchWeatherFromApi(String city) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .queryParam("units", units)
                        .queryParam("lang", lang)
                        .build())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
    }

    private void validateWeatherResponse(WeatherApiResponse weatherResponse) {
        if (weatherResponse == null) {
            throw new RuntimeException("Weather response is null");
        }

        if (weatherResponse.getMain() == null) {
            throw new RuntimeException("Main weather data is missing");
        }
    }

    private WeatherEntity createWeatherEntity(WeatherApiResponse weatherResponse) {
        Double temperature = weatherResponse.getMain().getTemp();
        String description = weatherResponse.getWeather() != null && !weatherResponse.getWeather().isEmpty()
                ? weatherResponse.getWeather().get(0).getDescription()
                : "No description";

        return new WeatherEntity(
                weatherResponse.getName(),
                temperature,
                LocalDateTime.now(),
                description
        );
    }

    public WeatherEntity fetchAndSaveWeather() {
        return fetchAndSaveWeatherForCity(defaultCity);
    }

    public WeatherEntity fetchAndSaveWeatherForCity(String city) {
        try {
            log.info("Fetching weather data for city: {}", city);

            WeatherApiResponse weatherResponse = fetchWeatherFromApi(city);
            validateWeatherResponse(weatherResponse);

            WeatherEntity weatherEntity = createWeatherEntity(weatherResponse);
            WeatherEntity saved = weatherRepository.save(weatherEntity);

            log.info("Weather data saved: {} - {}°C", saved.getCity(), saved.getTemperature());
            return saved;

        } catch (Exception e) {
            log.error("Error fetching weather data for city: {}", city, e);
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage(), e);
        }
    }

    public List<WeatherEntity> getAllWeatherRecords() {
        return weatherRepository.findAllOrderByTimestampDesc();
    }

    public List<WeatherEntity> getWeatherRecordsByCity(String city) {
        return weatherRepository.findByCityOrderByTimestampDesc(city);
    }
}