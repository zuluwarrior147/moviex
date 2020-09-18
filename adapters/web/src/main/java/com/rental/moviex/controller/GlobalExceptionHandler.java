package com.rental.moviex.controller;

import com.rental.moviex.exception.MovieNotFoundException;
import com.rental.moviex.exception.RentalNotFinishedException;
import com.rental.moviex.exception.RentalNotFoundException;
import com.rental.moviex.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, MovieNotFoundException.class, RentalNotFoundException.class})
    ResponseEntity<String> userNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RentalNotFinishedException.class)
    ResponseEntity<String> rentalNotFinishedException(RentalNotFinishedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
