package com.rental.moviex.application.port.in;

import lombok.Value;

import java.util.Map;
import java.util.Set;

public interface RentMoviesUseCase {

    RentedMoviesResponse rentMovie(RentMoviesCommand command);

    @Value
    class RentMoviesCommand {
        private final Long userId;
        private final Map<Long, Integer> moviesForDays;
    }

    @Value
    class RentedMoviesResponse {
        private final Long calculatedCost;
    }
}
