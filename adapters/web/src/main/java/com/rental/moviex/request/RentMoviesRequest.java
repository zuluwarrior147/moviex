package com.rental.moviex.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Data
public class RentMoviesRequest {
    private Long userId;
    private Collection<MovieForDay> moviesForDays;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovieForDay {
        private Long movieId;
        private Integer days;
    }

    public Map<Long, Integer> zipMoviesForDays() {
        return moviesForDays.stream()
                .collect(toMap(MovieForDay::getMovieId, MovieForDay::getDays));
    }
}
