package com.dannyhromau.weather.api.external;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalWeatherDto {

    private String location;
    private double temp;
    private double wind;
    private double pressure;
    private int humidity;
    private String condition;

    public ExternalWeatherDto(String location, double temp, double wind, double pressure, int humidity, String condition) {
        this.location = location;
        this.temp = temp;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
        this.condition = condition;
    }
}
