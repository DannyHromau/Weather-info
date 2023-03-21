package com.dannyhromau.weather.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weather")
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "location")
    private String location;
    @Column(name = "temp_c")
    private double temp;
    @Column(name = "wind_mph")
    private double wind;
    @Column(name = "pressure_mb")
    private double pressure;
    @Column(name = "humidity_per")
    private int humidity;
    @Column(name = "condition_text")
    private String condition;


    public Weather(String location, double temp, double wind, double pressure, int humidity, String condition) {
        this.location = location;
        this.temp = temp;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
        this.condition = condition;
    }


}
