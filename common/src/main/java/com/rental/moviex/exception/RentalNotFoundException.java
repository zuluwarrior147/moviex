package com.rental.moviex.exception;

import lombok.Getter;

@Getter
public class RentalNotFoundException extends RuntimeException{

    public RentalNotFoundException(long userId, long movieId) {
        super(String.format("Rental with user's id=%d and movie's id=%d not found", userId, movieId));
    }
}
