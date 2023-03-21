package com.dannyhromau.weather.service;

import com.dannyhromau.weather.WeatherApp;
import com.dannyhromau.weather.api.dto.response.ResponseAverageTempDto;
import com.dannyhromau.weather.api.dto.response.ResponseWeatherDto;
import com.dannyhromau.weather.api.external.ExternalWeatherDto;
import com.dannyhromau.weather.api.external.impl.WeatherapiProvider;
import com.dannyhromau.weather.exception.EmptyDataException;
import com.dannyhromau.weather.exception.EmptyParameterException;
import com.dannyhromau.weather.mapper.WeatherMapper;
import com.dannyhromau.weather.model.Weather;
import com.dannyhromau.weather.repository.WeatherRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testing of service")

@SpringBootTest(classes = WeatherApp.class)
@TestPropertySource("classpath:Application-test.yml")
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class WeatherServiceTest {



    @InjectMocks
    private WeatherService weatherService;
    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private WeatherapiProvider weatherapiProvider;

    private WeatherMapper weatherMapper = Mockito.mock(WeatherMapper.class, Answers.CALLS_REAL_METHODS);

    @BeforeEach
    void setup(){
        ReflectionTestUtils.setField(weatherService, "location", "Minsk");
    }

    @Test
    @DisplayName("Checking empty params")
    void checkingEmptyLocation()  {
        String location = "";
        Exception exception = assertThrows(EmptyParameterException.class, () ->
                WeatherService.validateLocation(location));
        String expectedMessage = "location parameter is missing";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @DisplayName("Checking valid location")
    void checkingValidLocation() throws EmptyParameterException{
        String location = "Minsk";
        WeatherService.validateLocation(location);

    }

    @Test
    @DisplayName("Checking valid params")
    void checkingValidParams() {
        weatherService.checkConfigParams();
    }

    @Test
    @DisplayName("Get responseWeatherDto`s entity from valid weather`s entity")
    void getValidDto() throws Exception{
        ResponseWeatherDto expectedDto = new ResponseWeatherDto("Minsk",
                2.0,
                3.0,
                4.0,
                50,
                "Sunny");
        Weather weather = new Weather("Minsk",
                2.0,
                3.0,
                4.0,
                50,
                "Sunny");
        when(weatherRepository.findByLocation(weather.getLocation())).thenReturn(weather);
        ResponseWeatherDto actualDto = weatherService.getActualWeather();
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Get exception when data does not exist")
    void getInValidDto(){
        Weather weather = new Weather("Minsk",
                2.0,
                3.0,
                4.0,
                50,
                "Sunny");
        when(weatherRepository.findByLocation(weather.getLocation())).thenReturn(null);
        Exception exception = assertThrows(EmptyDataException.class, () -> weatherService.getActualWeather());
        String expectedMessage = "WeatherEntity is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Get Invalid JSON")
    void getResponseAverageTempDto() throws Exception {
        LocalDate date = LocalDate.now();
        when(weatherapiProvider.getAverageTempsFromApi(date, date, "Minsk")).thenReturn(new JSONObject());
        Exception exception = assertThrows(JSONException.class, () -> weatherService.getAverageTemp(date, date));
        String expectedMessage = "No value for forecast";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
