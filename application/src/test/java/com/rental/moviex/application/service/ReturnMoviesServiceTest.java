package com.rental.moviex.application.service;

import com.rental.moviex.application.port.in.ReturnMoviesUseCase.ReturnMoviesCommand;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase.ReturnedMoviesResponse;
import com.rental.moviex.domain.Movie;
import com.rental.moviex.domain.MovieType;
import com.rental.moviex.domain.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReturnMoviesServiceTest extends BasicRentalTest {
    private ReturnMoviesService testedInstance;

    @BeforeEach
    void setUp() {
        super.setUp();
        testedInstance = new ReturnMoviesService(rentalPort, userPort, clock);
    }

    @Test
    void shouldCalculateNoSurchargesForRegularMovieIfNotExceeded() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.REGULAR, 2, 2);

        assertEquals(0L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateSurchargesForRegularMovie() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.REGULAR, 3, 5);

        assertEquals(60L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateSurchargesForOldMovie() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.OLD, 4, 6);

        assertEquals(30L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateNoSurchargesForOldMovieIfNotExceeded() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.OLD, 2, 4);

        assertEquals(0L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateSurchargeForRegularMovie() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.REGULAR, 4, 6);

        assertEquals(60L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateSurchargeForReleaseMovie() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.RELEASE, 1, 3);

        assertEquals(80L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateSurchargesForMultipleMovies() {
        Movie regularMovie = createMovie(MOVIE_ID, "Aviator", MovieType.REGULAR);
        Movie oldMovie = createMovie(OLD_MOVIE_ID, "Casino", MovieType.OLD);
        Movie newMovie = createMovie(NEW_MOVIE_ID, "The Irishman", MovieType.RELEASE);

        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, MOVIE_ID))
                .thenReturn(new Rental().setMovie(regularMovie).setInitialRentalDays(2)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(4)));
        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, OLD_MOVIE_ID))
                .thenReturn(new Rental().setMovie(oldMovie).setInitialRentalDays(3)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(5)));
        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, NEW_MOVIE_ID))
                .thenReturn(new Rental().setMovie(newMovie).setInitialRentalDays(1)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(3)));

        ReturnedMoviesResponse actualResult = testedInstance.returnMovies(
                new ReturnMoviesCommand(USER_ID, Set.of(MOVIE_ID, OLD_MOVIE_ID, NEW_MOVIE_ID)));

        assertEquals(110L, actualResult.getSurcharges());
    }

    @Test
    void shouldCalculateBonusPointsForRegularMovies() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.REGULAR, 2, 3);
        assertEquals(1, actualResult.getBonusPoints());
    }

    @Test
    void shouldCalculateBonusPointsForReleaseMovies() {
        ReturnedMoviesResponse actualResult = returnSingleMovie(MovieType.RELEASE, 2, 3);
        assertEquals(2, actualResult.getBonusPoints());
    }

    @Test
    void shouldCalculateBonusPointsForMultipleMovies() {
        Movie regularMovie = createMovie(MOVIE_ID, "Aviator", MovieType.REGULAR);
        Movie oldMovie = createMovie(OLD_MOVIE_ID, "Casino", MovieType.OLD);
        Movie newMovie = createMovie(NEW_MOVIE_ID, "The Irishman", MovieType.RELEASE);

        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, MOVIE_ID))
                .thenReturn(new Rental().setMovie(regularMovie).setInitialRentalDays(2)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(4)));
        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, OLD_MOVIE_ID))
                .thenReturn(new Rental().setMovie(oldMovie).setInitialRentalDays(3)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(5)));
        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, NEW_MOVIE_ID))
                .thenReturn(new Rental().setMovie(newMovie).setInitialRentalDays(1)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(3)));

        ReturnedMoviesResponse actualResult = testedInstance.returnMovies(
                new ReturnMoviesCommand(USER_ID, Set.of(MOVIE_ID, OLD_MOVIE_ID, NEW_MOVIE_ID)));

        assertEquals(4, actualResult.getBonusPoints());
    }

    @Test
    void shouldAssignUsersBonusPoints() {
        returnSingleMovie(MovieType.RELEASE, 3, 4);

        assertEquals(2, user.getDetails().getBonusPoints());
    }

    private ReturnedMoviesResponse returnSingleMovie(MovieType type, int initialDays, int actualDays) {
        Movie movie = createMovie(MOVIE_ID, "Gangs of New York", type);
        when(rentalPort.loadRentalByUserIdAndMovieId(USER_ID, MOVIE_ID))
                .thenReturn(new Rental().setMovie(movie).setInitialRentalDays(initialDays)
                        .setUser(user).setStartDate(LocalDate.now(clock).minusDays(actualDays)));

        return testedInstance.returnMovies(
                new ReturnMoviesCommand(USER_ID, Collections.singleton(MOVIE_ID)));
    }
}