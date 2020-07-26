package com.rental.moviex.adapter;

import com.rental.moviex.PersistenceAdapter;
import com.rental.moviex.application.port.out.UserPort;
import com.rental.moviex.domain.User;
import com.rental.moviex.exception.UserNotFoundException;
import com.rental.moviex.mapper.UserMapper;
import com.rental.moviex.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public User loadUserById(Long id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
