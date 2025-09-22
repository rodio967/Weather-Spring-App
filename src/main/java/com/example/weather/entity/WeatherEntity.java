package com.example.weather.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column
    private String description;


    public WeatherEntity(String city, Double temperature, LocalDateTime timestamp, String description) {
        this.city = city;
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.description = description;
    }

}