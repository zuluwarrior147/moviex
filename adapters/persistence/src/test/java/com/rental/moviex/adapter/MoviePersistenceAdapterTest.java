package com.rental.moviex.adapter;

import com.rental.moviex.domain.Movie;
import com.rental.moviex.domain.MovieType;
import com.rental.moviex.entity.MovieEntity;
import com.rental.moviex.exception.MovieNotFoundException;
import com.rental.moviex.mapper.MovieMapper;
import com.rental.moviex.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoviePersistenceAdapterTest {
    private static final long MOVIE_ID = 12L;
    private static final String TITLE = "Blade Runner";

    @Mock
    private MovieRepository movieRepository;
    private MovieMapper mapper;

    MoviePersistenceAdapter testedInstance;


    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MovieMapper.class);
        testedInstance = new MoviePersistenceAdapter(movieRepository, mapper);
    }

    @Test
    void shouldLoadMovieById() {
        LocalDate releaseDate = LocalDate.of(1982, 7, 25);
        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(new MovieEntity()
                .setTitle(TITLE).setType(MovieType.OLD)
                .setReleaseDate(releaseDate)));

        Movie movie = testedInstance.loadMovieById(MOVIE_ID);

        assertThat(movie).extracting(Movie::getTitle).isEqualTo(TITLE);
        assertThat(movie).extracting(Movie::getType).isEqualTo(MovieType.OLD);
        assertThat(movie).extracting(Movie::getReleaseDate).isEqualTo(releaseDate);
    }

    @Test
    void shouldThrowExceptionWhenNoMovieFound() {
        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> testedInstance.loadMovieById(MOVIE_ID));
    }
}