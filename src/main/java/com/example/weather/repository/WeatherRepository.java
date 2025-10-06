package com.example.weather.repository;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

@RegisterBeanMapper(WeatherEntity.class)
public interface WeatherRepository {
    @SqlUpdate("INSERT INTO weather_records (city, temperature, timestamp, description) " +
            "VALUES (:city, :temperature, :timestamp, :description)")
    @GetGeneratedKeys
    Long insert(@BindBean WeatherEntity weatherEntity);

    @SqlQuery("SELECT * FROM weather_records ORDER BY timestamp DESC")
    List<WeatherEntity> findAllOrderByTimestampDesc();

    @SqlQuery("SELECT * FROM weather_records WHERE city = :city ORDER BY timestamp DESC")
    List<WeatherEntity> findByCityOrderByTimestampDesc(@Bind("city") String city);
}