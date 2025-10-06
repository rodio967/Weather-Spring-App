package com.example.weather.service;

import com.example.weather.repository.WeatherEntity;
import com.example.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherApiProperties weatherApiProperties;
    private final WeatherRepository weatherRepository;
    private final WebClient webClient;


    private WeatherApiResponse fetchWeatherFromApi(String city) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("q", city)
                        .queryParam("appid", weatherApiProperties.getKey())
                        .queryParam("units", weatherApiProperties.getUnits())
                        .queryParam("lang", weatherApiProperties.getLang())
                        .build())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
    }

    private WeatherApiResponse validateWeatherResponse(WeatherApiResponse weatherResponse) {
        if (weatherResponse == null) {
            throw new RuntimeException("Weather response is null");
        }

        if (weatherResponse.main() == null) {
            throw new RuntimeException("Main weather data is missing");
        }

        return weatherResponse;
    }

    private WeatherEntity createWeatherEntity(WeatherApiResponse weatherResponse) {
        Double temperature = weatherResponse.main().temp();
        String description = weatherResponse.weather() != null && !weatherResponse.weather().isEmpty()
                ? weatherResponse.weather().get(0).description()
                : "No description";

        return new WeatherEntity(
                weatherResponse.name(),
                temperature,
                LocalDateTime.now(),
                description
        );
    }

    public WeatherEntity fetchAndSaveWeather() {
        return fetchAndSaveWeatherForCity(weatherApiProperties.getCity());
    }

    public WeatherEntity fetchAndSaveWeatherForCity(String city) {
        log.info("Fetching weather data for city: {}", city);

        WeatherApiResponse weatherResponse = validateWeatherResponse(fetchWeatherFromApi(city));
        WeatherEntity weatherEntity = createWeatherEntity(weatherResponse);

        Long id = weatherRepository.insert(weatherEntity);
        weatherEntity.setId(id);

        log.info("Weather data saved: {} - {}°C", weatherEntity.getCity(), weatherEntity.getTemperature());
        return weatherEntity;
    }

    public List<WeatherEntity> getAllWeatherRecords() {
        return weatherRepository.findAllOrderByTimestampDesc();
    }

    public List<WeatherEntity> getWeatherRecordsByCity(String city) {
        return weatherRepository.findByCityOrderByTimestampDesc(city);
    }
}