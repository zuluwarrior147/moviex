package com.rental.moviex.adapter;

import com.rental.moviex.domain.Movie;
import com.rental.moviex.domain.MovieType;
import com.rental.moviex.domain.Rental;
import com.rental.moviex.domain.User;
import com.rental.moviex.entity.MovieEntity;
import com.rental.moviex.entity.RentalEntity;
import com.rental.moviex.entity.UserDetailsEntity;
import com.rental.moviex.entity.UserEntity;
import com.rental.moviex.exception.RentalNotFoundException;
import com.rental.moviex.mapper.RentalMapper;
import com.rental.moviex.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalPersistenceAdapterTest {
    public static final long USER_ID = 32L;
    public static final String USER_EMAIL = "gaspar.noe@mail.com";
    private static final long MOVIE_ID = 12L;
    private static final String MOVIE_TITLE = "Climax";
    @Mock
    private RentalRepository rentalRepository;

    private RentalPersistenceAdapter testedInstance;
    private RentalMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RentalMapper.class);
        testedInstance = new RentalPersistenceAdapter(rentalRepository, mapper);
    }

    @Test
    void shouldLoadRentalByUserIdAndMovieId() {
        UserEntity user = new UserEntity()
                .setDetails(new UserDetailsEntity().setBonusPoints(3))
                .setEmail(USER_EMAIL);
        MovieEntity movie = new MovieEntity()
                .setTitle(MOVIE_TITLE).setType(MovieType.OLD)
                .setReleaseDate(LocalDate.of(2018, 2, 6));
        LocalDate rentalStart = LocalDate.now();
        RentalEntity rental = new RentalEntity().setUser(user).setMovie(movie).setStartDate(rentalStart);
        when(rentalRepository.findByUserIdAndMovieId(USER_ID, MOVIE_ID)).thenReturn(Optional.of(rental));

        Rental actualResult = testedInstance.loadRentalByUserIdAndMovieId(USER_ID, MOVIE_ID);

        assertThat(actualResult).extracting(Rental::getStartDate).isEqualTo(rentalStart);
        assertThat(actualResult).extracting(Rental::getMovie).isEqualToComparingFieldByField(movie);
        assertThat(actualResult).extracting(Rental::getUser).isEqualToIgnoringGivenFields(user, "details");
    }

    @Test
    void shouldThrowExceptionWhenNoRentalFound() {
        when(rentalRepository.findByUserIdAndMovieId(USER_ID, MOVIE_ID)).thenReturn(Optional.empty());

        assertThrows(RentalNotFoundException.class, () -> testedInstance.loadRentalByUserIdAndMovieId(USER_ID, MOVIE_ID));
    }

    @Test
    void shouldCreateNewRental() {
        User user = new User().setEmail(USER_EMAIL);
        Movie movie = new Movie().setTitle(MOVIE_TITLE).setType(MovieType.OLD);
        Rental rental = new Rental().setUser(user)
                .setMovie(movie);
        RentalEntity rentalEntity = new RentalEntity().setUser(new UserEntity().setEmail(USER_EMAIL))
                .setMovie(new MovieEntity().setTitle(MOVIE_TITLE).setType(MovieType.OLD));
        when(rentalRepository.save(any(RentalEntity.class))).thenReturn(rentalEntity);

        Rental savedRental = testedInstance.createRental(rental);

        assertThat(savedRental).isEqualToIgnoringGivenFields(rental, "user", "movie");
        assertThat(savedRental).extracting(Rental::getMovie).isEqualToComparingFieldByField(movie);
        assertThat(savedRental).extracting(Rental::getUser).isEqualToComparingFieldByField(user);
    }
}