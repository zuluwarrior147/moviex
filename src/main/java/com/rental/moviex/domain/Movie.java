package com.rental.moviex.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class Movie {
    private Long id;
    private String title;
    private MovieType type;
    private String description;
    private Duration duration;
    private LocalDate releaseDate;
}
