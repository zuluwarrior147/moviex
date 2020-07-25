package com.rental.moviex.application.service.calculation;

import com.rental.moviex.domain.MovieType;

import java.util.HashMap;
import java.util.Map;

public class RentalCalculationStrategyFactory {
    private final Map<MovieType, RentalCalculationStrategy> strategies;

    private RentalCalculationStrategyFactory() {
        strategies = new HashMap<>() {{
            put(MovieType.OLD, new OldMovieCalculationStrategy());
            put(MovieType.REGULAR, new RegularMovieCalculationStrategy());
            put(MovieType.RELEASE, new ReleaseMovieCalculationStrategy());
        }};
    }

    public RentalCalculationStrategy getRentalStrategy(MovieType type) {
        return strategies.get(type);
    }

    private static class FactoryHolder {
        private static final RentalCalculationStrategyFactory instance = new RentalCalculationStrategyFactory();
    }

    public static RentalCalculationStrategyFactory getInstance() {
        return FactoryHolder.instance;
    }


}
