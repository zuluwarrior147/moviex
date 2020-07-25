package com.rental.moviex.mapper;

import com.rental.moviex.domain.Rental;
import com.rental.moviex.entity.RentalEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapConfig.class)
public interface RentalMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "movie.createdAt", ignore = true)
    @Mapping(target = "user.createdAt", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    @Mapping(target = "movie.lastModified", ignore = true)
    @Mapping(target = "user.lastModified", ignore = true)
    RentalEntity map(Rental rental);

    @InheritInverseConfiguration
    @Mapping(target = "rentalCalculationStrategyFactory", ignore = true)
    Rental map(RentalEntity rentalEntity);
}
