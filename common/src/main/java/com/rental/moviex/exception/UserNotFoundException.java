package com.rental.moviex.exception;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super(format("User with id=%d is not found.", userId));
    }
}
