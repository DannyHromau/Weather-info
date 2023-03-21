package com.dannyhromau.weather.api.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseWeatherDto {

    private String location;
    private double temp_c;
    private double wind_mph;
    private double pressure_mb;
    private int humidity_per;
    private String condition;

    public ResponseWeatherDto(String location, double temp_c, double wind_mph, double pressure_mb, int humidity_per, String condition) {
        this.location = location;
        this.temp_c = temp_c;
        this.wind_mph = wind_mph;
        this.pressure_mb = pressure_mb;
        this.humidity_per = humidity_per;
        this.condition = condition;
    }
}
