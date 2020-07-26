package com.rental.moviex.application.service;

import com.rental.moviex.application.port.in.RentMoviesUseCase.RentMoviesCommand;
import com.rental.moviex.application.port.in.RentMoviesUseCase.RentedMoviesResponse;
import com.rental.moviex.domain.Movie;
import com.rental.moviex.domain.MovieType;
import com.rental.moviex.domain.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RentMovieServiceTest extends BasicRentalTest {
    private static final int SINGLE_DAY = 1;
    private RentMoviesService testedInstance;

    @BeforeEach
    void setUp() {
        super.setUp();
        testedInstance = new RentMoviesService(moviePort, userPort, rentalPort, clock);
        when(rentalPort.createRental(any(Rental.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, Rental.class));
    }

    @Test
    public void shouldRentMovieStartingWithCurrentDate() {
        Movie movie = new Movie().setId(MOVIE_ID)
                .setTitle("The Departed").setType(MovieType.OLD);
        when(moviePort.loadMovieById(MOVIE_ID)).thenReturn(movie);

        testedInstance.rentMovies(new RentMoviesCommand(USER_ID, Map.of(MOVIE_ID, SINGLE_DAY)));

        verify(rentalPort).createRental(new Rental().setUser(user).setMovie(movie)
                .setInitialRentalDays(SINGLE_DAY).setStartDate(LocalDate.now(clock)));
    }

    @Test
    public void shouldCalculateOldMoviesEstimatedBasicCost() {
        createMovie(MOVIE_ID, "Goodfellas", MovieType.OLD);
        createMovie(OLD_MOVIE_ID, "Taxi Driver", MovieType.OLD);

        RentedMoviesResponse actualResult = testedInstance
                .rentMovies(new RentMoviesCommand(USER_ID, Map.of(MOVIE_ID, SINGLE_DAY, OLD_MOVIE_ID, SINGLE_DAY)));

        assertEquals(60L, actualResult.getCalculatedCost());
    }

    @Test
    void shouldCalculateMixedMoviesEstimatedBasicCost() {
        createMovie(MOVIE_ID, "Aviator", MovieType.REGULAR);
        createMovie(OLD_MOVIE_ID, "Casino", MovieType.OLD);
        createMovie(NEW_MOVIE_ID, "The Irishman", MovieType.RELEASE);

        RentedMoviesResponse actualResult = testedInstance
                .rentMovies(new RentMoviesCommand(USER_ID,
                        Map.of(MOVIE_ID, SINGLE_DAY, OLD_MOVIE_ID, SINGLE_DAY, NEW_MOVIE_ID, SINGLE_DAY)));

        assertEquals(100L, actualResult.getCalculatedCost());
    }

    @Test
    void shouldCalculateMoviesExceededEstimatedCost() {
        createMovie(MOVIE_ID, "Aviator", MovieType.REGULAR);
        createMovie(OLD_MOVIE_ID, "Casino", MovieType.OLD);
        createMovie(NEW_MOVIE_ID, "The Irishman", MovieType.RELEASE);

        RentedMoviesResponse actualResult = testedInstance
                .rentMovies(new RentMoviesCommand(USER_ID,
                        Map.of(MOVIE_ID, 4, OLD_MOVIE_ID, 7, NEW_MOVIE_ID, 2)));

        assertEquals(230L, actualResult.getCalculatedCost());
    }
}