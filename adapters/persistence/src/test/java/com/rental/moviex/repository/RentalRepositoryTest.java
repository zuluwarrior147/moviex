package com.rental.moviex.repository;

import com.rental.moviex.domain.MovieType;
import com.rental.moviex.entity.MovieEntity;
import com.rental.moviex.entity.RentalEntity;
import com.rental.moviex.entity.UserDetailsEntity;
import com.rental.moviex.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RentalRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RentalRepository testedInstance;

    @Test
    void shouldSaveAndRetrieveNewRental() {
        UserEntity user = userRepository.save(new UserEntity()
                .setDetails(new UserDetailsEntity()).setEmail("ridley.scott@mail.com"));
        MovieEntity movie = movieRepository.save(new MovieEntity().setTitle("Alien").setType(MovieType.OLD));
        RentalEntity rental = new RentalEntity().setStartDate(LocalDate.now())
                .setMovie(movie).setUser(user).setInitialRentalDays(5);

        testedInstance.save(rental);

        Optional<RentalEntity> actualResult = testedInstance.findByUserIdAndMovieId(user.getId(), movie.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult).get().isEqualToIgnoringGivenFields(rental, "id", "user", "movie");
        assertThat(actualResult).get().extracting(RentalEntity::getUser).isEqualToComparingFieldByField(user);
        assertThat(actualResult).get().extracting(RentalEntity::getMovie).isEqualToComparingFieldByField(movie);
    }
}
