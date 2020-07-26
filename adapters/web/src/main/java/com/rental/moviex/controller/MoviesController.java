package com.rental.moviex.controller;

import com.rental.moviex.WebAdapter;
import com.rental.moviex.application.port.in.RentMoviesUseCase;
import com.rental.moviex.application.port.in.RentMoviesUseCase.RentMoviesCommand;
import com.rental.moviex.application.port.in.RentMoviesUseCase.RentedMoviesResponse;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase.ReturnMoviesCommand;
import com.rental.moviex.application.port.in.ReturnMoviesUseCase.ReturnedMoviesResponse;
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
        RentedMoviesResponse rentedMoviesResponse = rentMoviesUseCase.rentMovie(
                new RentMoviesCommand(request.getUserId(), request.zipMoviesForDays()));
        return ResponseEntity.ok(rentedMoviesResponse);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnedMoviesResponse> returnMovies(@RequestBody ReturnMoviesCommand request) {
        ReturnedMoviesResponse rentedMoviesResponse = returnMoviesUseCase.returnMovies(request);
        return ResponseEntity.ok(rentedMoviesResponse);
    }
}
