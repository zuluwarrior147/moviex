package com.rental.moviex.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class RentalNotFinishedException extends RuntimeException {

    public RentalNotFinishedException(long id) {
        super(format("Rental with id=%d is not yet finished.", id));
    }
}
