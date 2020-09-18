package com.rental.moviex.application.port;

import com.rental.moviex.domain.Movie;

public interface MoviePort {
    Movie loadMovieById(Long id);
}
