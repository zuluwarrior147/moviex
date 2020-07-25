package com.rental.moviex.application.service;

import com.rental.moviex.application.port.out.MoviePort;
import com.rental.moviex.application.port.out.RentalPort;
import com.rental.moviex.application.port.out.UserPort;
import com.rental.moviex.domain.Movie;
import com.rental.moviex.domain.MovieType;
import com.rental.moviex.domain.User;
import com.rental.moviex.domain.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
abstract class BasicRentalTest {
    static final long USER_ID = 21L;
    static final String USER_EMAIL = "mscorsese@mail.com";
    static final long MOVIE_ID = 13L;
    static final long OLD_MOVIE_ID = 43L;
    static final long NEW_MOVIE_ID = 112L;

    @Mock
    UserPort userPort;
    @Mock
    MoviePort moviePort;
    @Mock
    RentalPort rentalPort;
    User user;
    Clock clock;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        user = new User().setId(USER_ID).setEmail(USER_EMAIL).setDetails(new UserDetails());
        when(userPort.loadUserById(USER_ID)).thenReturn(user);
    }

    Movie createMovie(Long id, String title, MovieType type) {
        Movie movie = new Movie().setId(id).setTitle(title).setType(type);
        lenient().when(moviePort.loadMovieById(id)).thenReturn(movie);
        return movie;
    }

}
