package com.rental.moviex.adapter;

import com.rental.moviex.domain.User;
import com.rental.moviex.domain.UserDetails;
import com.rental.moviex.entity.UserDetailsEntity;
import com.rental.moviex.entity.UserEntity;
import com.rental.moviex.mapper.UserMapper;
import com.rental.moviex.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {
    private static final long USER_ID = 21L;
    private static final String USER_EMAIL = "denny.villeneuve@mail.com";
    private static final String USER_NAME = "Denny";
    @Mock
    private UserRepository repository;

    private UserPersistenceAdapter testedInstance;

    @BeforeEach
    void setUp() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        testedInstance = new UserPersistenceAdapter(repository, mapper);
    }

    @Test
    void shouldLoadUserById() {
        when(repository.findById(USER_ID)).thenReturn(Optional.of(new UserEntity()
                .setEmail("denny.villeneuve@mail.com").setDetails(
                        new UserDetailsEntity().setFirstName(USER_NAME)
                                .setBonusPoints(12))));

        User user = testedInstance.loadUserById(USER_ID);

        assertThat(user).extracting(User::getEmail).isEqualTo(USER_EMAIL);
        assertThat(user).extracting(User::getDetails).extracting(UserDetails::getBonusPoints).isEqualTo(12);
        assertThat(user).extracting(User::getDetails).extracting(UserDetails::getFirstName).isEqualTo(USER_NAME);
    }

    @Test
    void shouldThrowExceptionIfNoUserFound() {
        when(repository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> testedInstance.loadUserById(USER_ID));
    }
}