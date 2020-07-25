package com.rental.moviex.mapper;

import org.mapstruct.MapperConfig;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.ERROR;

@MapperConfig(
        nullValueCheckStrategy = ALWAYS,
        unmappedTargetPolicy = ERROR,
        componentModel = "spring"
)
public interface MapConfig {
}
