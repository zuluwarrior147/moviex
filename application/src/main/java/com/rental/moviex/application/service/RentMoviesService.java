package com.rental.moviex.application.service;

import com.rental.moviex.UseCase;
import com.rental.moviex.application.port.in.RentMoviesUseCase;
import com.rental.moviex.application.port.out.MoviePort;
import com.rental.moviex.application.port.out.RentalPort;
import com.rental.moviex.application.port.out.UserPort;
import com.rental.moviex.domain.Movie;
import com.rental.moviex.domain.Rental;
import com.rental.moviex.domain.User;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;

@RequiredArgsConstructor
@UseCase
public class RentMoviesService implements RentMoviesUseCase {

    private final MoviePort moviePort;
    private final UserPort userPort;
    private final RentalPort rentalPort;
    private final Clock clock;

    public RentedMoviesResponse rentMovies(RentMoviesCommand command) {
        User user = userPort.loadUserById(command.getUserId());
        Long basicRent = command.getMoviesForDays()
                .entrySet().stream()
                .map(e -> createRental(e.getKey(), e.getValue()))
                .map(rental -> rental.setUser(user))
                .map(rentalPort::createRental)
                .mapToLong(Rental::calculateBasicRent)
                .sum();
        return new RentedMoviesResponse(basicRent);
    }


    private Rental createRental(Long movieId, Integer days) {
        Movie movie = moviePort.loadMovieById(movieId);
        return new Rental()
                .setMovie(movie)
                .setInitialRentalDays(days)
                .setStartDate(LocalDate.now(clock));
    }
}

