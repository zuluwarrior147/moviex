package com.rental.moviex.application.port.out;

import com.rental.moviex.domain.Rental;

public interface RentalPort {
    Rental createRental(Rental rental);

    Rental loadRentalByUserIdAndMovieId(Long userId, Long movieId);
}
