package com.dannyhromau.weather.api.external.impl;


import com.dannyhromau.weather.api.external.ExternalApiProvider;
import com.dannyhromau.weather.api.external.ExternalWeatherDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class WeatherapiProvider implements ExternalApiProvider {

    private static final String HEADER_API_KEY = "244edebae2msh5893d7fc3fb0300p1a7662jsn26ce9a824a0b";
    private static final String HEADER_API_HOST = "weatherapi-com.p.rapidapi.com";
    private final RestTemplate restTemplate = new RestTemplate();
    private static HttpEntity entity;


    @Override
    public ExternalWeatherDto getWeatherFromApi(String location) throws JSONException {
        String url = "https://weatherapi-com.p.rapidapi.com/current.json?q={location}";
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, location);
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONObject weatherapiJsonObject = jsonObject.getJSONObject("current");
        ExternalWeatherDto externalWeatherDto = new ExternalWeatherDto(location,
                weatherapiJsonObject.getDouble("temp_c"),
                weatherapiJsonObject.getDouble("wind_mph"),
                weatherapiJsonObject.getDouble("pressure_mb"),
                weatherapiJsonObject.getInt("humidity"),
                weatherapiJsonObject.getJSONObject("condition").getString("text"));
        return externalWeatherDto;
    }

    @Override
    public JSONObject getAverageTempsFromApi(LocalDate fromDate, LocalDate toDate, String location) throws JSONException {
        String url = "https://weatherapi-com.p.rapidapi.com/history.json";
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url)
                .append("?q=").append(location)
                .append("&dt=").append("2023-03-17")
                .append("&end_dt=").append(toDate)
                .append("&hour=").append("-1");
        HttpEntity<String> response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, entity, String.class);

        return new JSONObject(response.getBody());
    }

    @PostConstruct
    static void initHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", HEADER_API_KEY);
        headers.add("X-RapidAPI-Host", HEADER_API_HOST);
        entity = new HttpEntity<>(headers);
    }


}
