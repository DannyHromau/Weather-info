package com.dannyhromau.weather.api.external;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface ExternalApiProvider {


    ExternalWeatherDto getWeatherFromApi(String location) throws JSONException;

    JSONObject getAverageTempsFromApi(LocalDate fromDate, LocalDate toDate, String location) throws JSONException;
}
