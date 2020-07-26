package com.rental.moviex.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

public interface ReturnMoviesUseCase {
    ReturnedMoviesResponse returnMovies(ReturnMoviesCommand command);


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class ReturnedMoviesResponse {
        private long surcharges;
        private int bonusPoints;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class ReturnMoviesCommand {
        private Long userId;
        private Set<Long> moviesIds;
    }
}
