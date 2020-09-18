package com.rental.moviex.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Map;

public interface RentMoviesUseCase {

    RentedMoviesResponse rentMovies(RentMoviesCommand command);

    @Value
    class RentMoviesCommand {
        private final Long userId;
        private final Map<Long, Integer> moviesForDays;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class RentedMoviesResponse {
        private Long calculatedCost;
    }
}
