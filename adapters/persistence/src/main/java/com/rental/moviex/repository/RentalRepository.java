package com.rental.moviex.repository;

import com.rental.moviex.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

    Optional<RentalEntity> findByUserIdAndMovieId(Long userId, Long movieId);
}
