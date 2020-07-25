package com.rental.moviex.domain.calculation;

import com.rental.moviex.domain.Rental;

public interface RentalCalculationStrategy {
    Long calculateSurcharges(Rental rental);

    Long calculateBasicRent(Rental rental);

    int getBonusPoints();
}
