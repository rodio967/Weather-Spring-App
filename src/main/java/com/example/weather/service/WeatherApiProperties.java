package com.example.weather.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiProperties {
    @NotBlank(message = "Weather API key must not be blank")
    private String key;


    @NotBlank(message = "Weather API URL must not be blank")
    @Pattern(regexp = "^https?://.*", message = "API URL must start with http:// or https://")
    private String url;


    @NotBlank(message = "Units must not be blank")
    @Pattern(regexp = "^(metric|imperial|standard)$", message = "Units must be one of: metric, imperial, standard")
    private String units;


    @NotBlank(message = "Language must not be blank")
    @Pattern(regexp = "^[a-z]{2}$", message = "Language must be a 2 letter code")
    private String lang;

    @NotBlank(message = "City must not be blank")
    private String city;
}