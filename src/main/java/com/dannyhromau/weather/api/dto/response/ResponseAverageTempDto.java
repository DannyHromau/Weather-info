package com.dannyhromau.weather.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAverageTempDto {

    private double avgtemp_c;
    private String error;

}
