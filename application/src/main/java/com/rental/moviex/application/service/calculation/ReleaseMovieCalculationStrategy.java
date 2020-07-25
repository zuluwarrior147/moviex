package com.rental.moviex.application.service.calculation;

import com.rental.moviex.domain.Rental;

class ReleaseMovieCalculationStrategy extends AbstractCalculationStrategy {
    private static final Long BASIC_RENT = 40L;
    private static final int BASIC_RENTAL_DAYS = 1;
    private static final int BONUS_POINTS = 2;

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
