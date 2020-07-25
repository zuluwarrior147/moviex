package com.rental.moviex.repository;

import com.rental.moviex.domain.MovieType;
import com.rental.moviex.entity.MovieEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository repository;

    @Test
    void shouldSaveAndRetrieveNewMovie() {
        MovieEntity movie = new MovieEntity().setTitle("No country for old men")
                .setDuration(Duration.ofHours(2).plusMinutes(3))
                .setType(MovieType.REGULAR)
                .setReleaseDate(LocalDate.of(2007, 2, 28));

        MovieEntity savedMovie = repository.save(movie);

        Optional<MovieEntity> actualMovie = repository.findById(savedMovie.getId());

        assertThat(actualMovie).isPresent();
        assertThat(actualMovie).get().isEqualToIgnoringGivenFields(movie, "id");
    }
}
