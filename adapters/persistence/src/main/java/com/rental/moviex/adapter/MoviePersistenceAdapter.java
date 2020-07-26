package com.rental.moviex.adapter;

import com.rental.moviex.PersistenceAdapter;
import com.rental.moviex.application.port.out.MoviePort;
import com.rental.moviex.domain.Movie;
import com.rental.moviex.exception.MovieNotFoundException;
import com.rental.moviex.mapper.MovieMapper;
import com.rental.moviex.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;


@PersistenceAdapter
@RequiredArgsConstructor
public class MoviePersistenceAdapter implements MoviePort {

    private final MovieRepository repository;
    private final MovieMapper mapper;


    @Override
    public Movie loadMovieById(Long id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
}
