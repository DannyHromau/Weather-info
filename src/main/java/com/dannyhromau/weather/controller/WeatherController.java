package com.dannyhromau.weather.controller;


import com.dannyhromau.weather.api.dto.request.RequestAverageTempDto;
import com.dannyhromau.weather.api.dto.response.ResponseAverageTempDto;
import com.dannyhromau.weather.api.dto.response.ResponseWeatherDto;
import com.dannyhromau.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("api/weather/city")

    public ResponseEntity<ResponseWeatherDto> getCityWeather() {

        ResponseWeatherDto responseWeatherDto;

        try {

            responseWeatherDto = weatherService.getActualWeather();

            return new ResponseEntity<>(responseWeatherDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("api/weather/temp/average")
    public ResponseEntity<ResponseAverageTempDto> getAverageTemperature(@RequestBody RequestAverageTempDto requestAverageTempDto) {

        ResponseAverageTempDto responseAverageTempDto = new ResponseAverageTempDto();
        try {
            responseAverageTempDto = weatherService.getAverageTemp(
                    requestAverageTempDto.getFromDate(), requestAverageTempDto.getToDate());
        } catch (Exception e) {
            responseAverageTempDto.setError(e.getMessage());
            return new ResponseEntity<>(responseAverageTempDto, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseAverageTempDto, HttpStatus.OK);

    }
}
