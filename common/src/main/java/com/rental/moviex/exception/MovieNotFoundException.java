package com.rental.moviex.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(long movieId) {
        super(format("Movie with id=%d is not found.", movieId));
    }
}
