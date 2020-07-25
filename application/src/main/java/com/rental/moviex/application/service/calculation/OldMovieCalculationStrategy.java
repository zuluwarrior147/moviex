package com.rental.moviex.application.service.calculation;

import com.rental.moviex.domain.Rental;

class OldMovieCalculationStrategy extends AbstractCalculationStrategy {
    private static final long BASIC_RENT = 30L;
    private static final int BASIC_RENTAL_DAYS = 5;
    private static final int BONUS_POINTS = 1;

    @Override
    public Long calculateSurcharges(Rental rental) {
        return calculateSurcharges(rental, BASIC_RENT, BASIC_RENTAL_DAYS);
    }

    @Override
    public Long calculateBasicRent(Rental rental) {
        return calculateBasicRent(rental, BASIC_RENT, BASIC_RENTAL_DAYS);
    }

    @Override
    public int getBonusPoints() {
        return BONUS_POINTS;
    }
}
