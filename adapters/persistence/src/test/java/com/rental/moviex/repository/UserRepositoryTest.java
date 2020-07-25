package com.rental.moviex.repository;

import com.rental.moviex.entity.UserDetailsEntity;
import com.rental.moviex.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void shouldGenerateUserIdWhenSaving() {
        UserEntity user = new UserEntity().setDetails(new UserDetailsEntity())
                .setEmail("clint.eastwood@mail.com");

        UserEntity savedUser = repository.save(user);

        assertNotNull(savedUser.getId());
    }

    @Test
    void shouldSaveAndRetrieveUser() {
        UserEntity user = new UserEntity().setDetails(new UserDetailsEntity())
                .setEmail("francis.coppola@mail.com");

        UserEntity savedUser = repository.save(user);

        Optional<UserEntity> actualUser = repository.findById(savedUser.getId());

        assertThat(actualUser).isPresent();
        assertThat(actualUser).get().isEqualToIgnoringGivenFields(user, "id");
    }

    @Test
    void shouldThrowExceptionWhenDuplicatedEmail() {
        UserEntity user = new UserEntity().setDetails(new UserDetailsEntity())
                .setEmail("darren.aronofsky@mail.com");
        UserEntity duplicatedUser = new UserEntity().setDetails(new UserDetailsEntity())
                .setEmail("darren.aronofsky@mail.com");

        repository.save(user);

        assertThrows(DataIntegrityViolationException.class, () -> repository.save(duplicatedUser));
    }
}
