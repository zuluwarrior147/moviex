package com.rental.moviex.application.exception;

import com.rental.moviex.domain.Rental;

import static java.lang.String.format;

public class RentalNotFinishedException extends RuntimeException {
    public RentalNotFinishedException(Rental rental) {
        super(format("Rental with id=%d is not yet finished.", rental.getId()));
    }
}
