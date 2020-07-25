package com.rental.moviex.domain;

import com.rental.moviex.domain.calculation.RentalCalculationStrategy;
import com.rental.moviex.domain.calculation.RentalCalculationStrategyFactory;
import com.rental.moviex.application.exception.RentalNotFinishedException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Data
@Accessors(chain = true)
public class Rental {
    private Long id;
    private User user;
    private Movie movie;
    private Integer initialRentalDays;
    private LocalDate startDate;
    private LocalDate endDate;

    private RentalCalculationStrategyFactory rentalCalculationStrategyFactory = RentalCalculationStrategyFactory.getInstance();

    public Long calculateSurcharges() {
        RentalCalculationStrategy rentalCalculationStrategy = rentalCalculationStrategyFactory.getRentalStrategy(movie.getType());
        return ofNullable(endDate)
                .map(d -> rentalCalculationStrategy.calculateSurcharges(this))
                .orElseThrow(() -> new RentalNotFinishedException(this));
    }

    public Long calculateBasicRent() {
        RentalCalculationStrategy rentalCalculationStrategy = rentalCalculationStrategyFactory.getRentalStrategy(movie.getType());
        return rentalCalculationStrategy.calculateBasicRent(this);
    }

    public Integer getBonusPoints() {
        RentalCalculationStrategy rentalCalculationStrategy = rentalCalculationStrategyFactory.getRentalStrategy(movie.getType());
        return ofNullable(endDate)
                .map(d -> rentalCalculationStrategy.getBonusPoints())
                .orElseThrow(() -> new RentalNotFinishedException(this));
    }
}
