package com.rental.moviex.controller;

import com.rental.moviex.WebAdapter;
import com.rental.moviex.application.usecase.RentMoviesUseCase;
import com.rental.moviex.application.usecase.RentMoviesUseCase.RentMoviesCommand;
import com.rental.moviex.application.usecase.RentMoviesUseCase.RentedMoviesResponse;
import com.rental.moviex.application.usecase.ReturnMoviesUseCase;
import com.rental.moviex.application.usecase.ReturnMoviesUseCase.ReturnMoviesCommand;
import com.rental.moviex.application.usecase.ReturnMoviesUseCase.ReturnedMoviesResponse;
import com.rental.moviex.request.RentMoviesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MoviesController {
    private final RentMoviesUseCase rentMoviesUseCase;
    private final ReturnMoviesUseCase returnMoviesUseCase;

    @PostMapping("/rent")
    public ResponseEntity<RentedMoviesResponse> rentMovies(@RequestBody RentMoviesRequest request) {
        RentedMoviesResponse rentedMoviesResponse = rentMoviesUseCase.rentMovies(
                new RentMoviesCommand(request.getUserId(), request.zipMoviesForDays()));
        return ResponseEntity.ok(rentedMoviesResponse);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnedMoviesResponse> returnMovies(@RequestBody ReturnMoviesCommand request) {
        ReturnedMoviesResponse rentedMoviesResponse = returnMoviesUseCase.returnMovies(request);
        return ResponseEntity.ok(rentedMoviesResponse);
    }
}
