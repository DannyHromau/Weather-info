package com.dannyhromau.weather.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestAverageTempDto {

    private LocalDate fromDate;
    private LocalDate toDate;


}
