package com.rental.moviex.domain.calculation;

import com.rental.moviex.domain.Rental;

import static java.lang.Integer.max;
import static java.time.temporal.ChronoUnit.DAYS;

abstract class AbstractCalculationStrategy implements RentalCalculationStrategy {

    Long calculateSurcharges(Rental rental, long basicRent, int basicRentalDays) {
        long daysRented = DAYS.between(rental.getStartDate(), rental.getEndDate());
        long basicRentalDaysLeft = Long.max(basicRentalDays - rental.getInitialRentalDays(), 0);
        long exceededDays = Long.max(daysRented - (rental.getInitialRentalDays() + basicRentalDaysLeft), 0);
        return exceededDays * basicRent;
    }

    Long calculateBasicRent(Rental rental, long basicRent, int basicRentalDays) {
        int exceededRentalDays = max(rental.getInitialRentalDays() - basicRentalDays, 0);
        return basicRent + exceededRentalDays * basicRent;
    }
}
