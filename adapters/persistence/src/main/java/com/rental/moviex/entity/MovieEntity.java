package com.rental.moviex.entity;

import com.rental.moviex.domain.MovieType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "movie")
@Getter
@Setter
@Accessors(chain = true)
@SequenceGenerator(name = "default_generator", sequenceName = "movie_seq", allocationSize = 25)
public class MovieEntity extends BaseEntity {
    private String title;
    private MovieType type;
    private String description;
    private Duration duration;
    private LocalDate releaseDate;
}
