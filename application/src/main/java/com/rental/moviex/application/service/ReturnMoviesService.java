package com.rental.moviex.application.service;

import com.rental.moviex.UseCase;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase;
import com.rental.moviex.application.port.out.RentalPort;
import com.rental.moviex.application.port.out.UserPort;
import com.rental.moviex.domain.Rental;
import com.rental.moviex.domain.User;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
public class ReturnMoviesService implements ReturnMoviesUseCase {
    private final RentalPort rentalPort;
    private final UserPort userPort;
    private final Clock clock;

    @Override
    @Transactional
    public ReturnedMoviesResponse returnMovies(ReturnMoviesCommand command) {
        List<Rental> returnedRentals = command.getMoviesIds().stream()
                .map(movieId -> rentalPort.loadRentalByUserIdAndMovieId(command.getUserId(), movieId))
                .map(rental -> rental.setEndDate(LocalDate.now(clock)))
                .collect(Collectors.toList());
        long surcharges = returnedRentals.stream()
                .mapToLong(Rental::calculateSurcharges)
                .sum();
        int bonusPoints = returnedRentals.stream()
                .mapToInt(Rental::getBonusPoints)
                .sum();
        assignUsersBonusPoints(command.getUserId(), bonusPoints);
        return new ReturnedMoviesResponse(surcharges, bonusPoints);
    }

    private void assignUsersBonusPoints(Long userId, int bonusPoints) {
        User user = userPort.loadUserById(userId);
        user.addBonusPoints(bonusPoints);
    }
}

