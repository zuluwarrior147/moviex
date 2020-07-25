package com.rental.moviex.mapper;

import com.rental.moviex.domain.Movie;
import com.rental.moviex.entity.MovieEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapConfig.class)
public interface MovieMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    MovieEntity map(Movie movie);

    @InheritInverseConfiguration
    Movie map(MovieEntity movieEntity);
}
