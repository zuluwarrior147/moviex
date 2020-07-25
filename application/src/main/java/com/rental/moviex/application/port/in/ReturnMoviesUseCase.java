package com.rental.moviex.application.port.in;

import lombok.Value;

import java.util.Set;

public interface ReturnMoviesUseCase {
    ReturnedMoviesResponse returnMovies(ReturnMoviesCommand command);

    @Value
    class ReturnedMoviesResponse {
        private final long surcharges;
        private final int bonusPoints;
    }

    @Value
    class ReturnMoviesCommand {
        private final Long userId;
        private final Set<Long> moviesIds;
    }
}
