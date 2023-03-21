package com.dannyhromau.weather.repository;


import com.dannyhromau.weather.model.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Integer> {

    Weather findByLocation(String location);
}
