package com.dannyhromau.weather.service;

import com.dannyhromau.weather.api.dto.response.ResponseAverageTempDto;
import com.dannyhromau.weather.api.dto.response.ResponseWeatherDto;
import com.dannyhromau.weather.api.external.ExternalWeatherDto;
import com.dannyhromau.weather.api.external.impl.WeatherapiProvider;
import com.dannyhromau.weather.exception.EmptyDataException;
import com.dannyhromau.weather.exception.EmptyParameterException;
import com.dannyhromau.weather.mapper.WeatherMapper;
import com.dannyhromau.weather.model.Weather;
import com.dannyhromau.weather.repository.WeatherRepository;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
@Log4j2
@EnableScheduling
@EnableAsync
public class WeatherService {
    @Value("${location}")
    private String location;

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private WeatherapiProvider weatherapiProvider;
    @Autowired
    private WeatherMapper weatherMapper;

    @PostConstruct
    public void checkConfigParams() {
        try {
            validateLocation(location);
        } catch (EmptyParameterException e) {
            log.error("location parameter is missing");
        }
    }

    public static void validateLocation(String location) throws EmptyParameterException {
        if (location.isEmpty()) {
            throw new EmptyParameterException("location parameter is missing", location);
        }

    }


    @Scheduled(fixedRateString = "${updateInterval}")
    @Async

    public void saveActualWeather() {
        try {
            validateLocation(location);
            ExternalWeatherDto externalWeatherDto = weatherapiProvider.getWeatherFromApi(location);
            Weather weather = weatherRepository.findByLocation(location);
            weather = weather == null ? new Weather() : weather;
            weatherMapper.updateWeatherFromDto(externalWeatherDto, weather);
            weatherRepository.save(weather);

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    public ResponseWeatherDto getActualWeather() throws EmptyDataException, EmptyParameterException {
        validateLocation(location);
        Weather weather = weatherRepository.findByLocation(location);
        if (weather == null) {
            log.warn("Data is not found");
            throw new EmptyDataException("WeatherEntity is null");
        }
        return new ResponseWeatherDto(weather.getLocation(),
                weather.getTemp(),
                weather.getWind(),
                weather.getPressure(),
                weather.getHumidity(),
                weather.getCondition());

    }

    public ResponseAverageTempDto getAverageTemp(LocalDate fromDate, LocalDate toDate) throws Exception {
        validateLocation(location);
        ResponseAverageTempDto responseAverageTempDto = new ResponseAverageTempDto();
        double tempSum = 0;
        double averageTemp;
        JSONArray jsonArray = weatherapiProvider.getAverageTempsFromApi(fromDate, toDate, location)
                .getJSONObject("forecast")
                .getJSONArray("forecastday");
        if (jsonArray.length() == 0) {
            throw new EmptyDataException("No data");
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            double daysAverageTemp = jsonArray.getJSONObject(i).getJSONObject("day").getDouble("avgtemp_c");
            tempSum += daysAverageTemp;
        }
        averageTemp = tempSum / jsonArray.length();
        averageTemp = (double) (Math.round(averageTemp * 10)) / 10;
        responseAverageTempDto.setAvgtemp_c(averageTemp);

        return responseAverageTempDto;
    }
}
