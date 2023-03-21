package com.dannyhromau.weather.mapper;

import com.dannyhromau.weather.api.external.ExternalWeatherDto;
import com.dannyhromau.weather.model.Weather;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    void updateWeatherFromDto(ExternalWeatherDto dto, @MappingTarget Weather weather);

}
