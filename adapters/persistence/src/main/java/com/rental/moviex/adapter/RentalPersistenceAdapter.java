package com.rental.moviex.adapter;

import com.rental.moviex.PersistenceAdapter;
import com.rental.moviex.application.port.out.RentalPort;
import com.rental.moviex.domain.Rental;
import com.rental.moviex.entity.RentalEntity;
import com.rental.moviex.mapper.RentalMapper;
import com.rental.moviex.repository.RentalRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@PersistenceAdapter
@RequiredArgsConstructor
public class RentalPersistenceAdapter implements RentalPort {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Override
    public Rental createRental(Rental rental) {
        RentalEntity savedRental = rentalRepository.save(rentalMapper.map(rental));
        return rentalMapper.map(savedRental);
    }

    @Override
    public Rental loadRentalByUserIdAndMovieId(Long userId, Long movieId) {
        return rentalRepository.findByUserIdAndMovieId(userId, movieId)
                .map(rentalMapper::map)
                .orElseThrow(EntityNotFoundException::new);
    }
}
