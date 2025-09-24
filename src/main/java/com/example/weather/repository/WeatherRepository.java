package com.example.weather.repository;

import com.example.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    @Query("SELECT w FROM WeatherEntity w ORDER BY w.timestamp DESC")
    List<WeatherEntity> findAllOrderByTimestampDesc();

    @Query("SELECT w FROM WeatherEntity w WHERE w.city = ?1 ORDER BY w.timestamp DESC")
    List<WeatherEntity> findByCityOrderByTimestampDesc(String city);
}