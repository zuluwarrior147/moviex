package com.rental.moviex.application.port.out;

import com.rental.moviex.domain.User;

public interface UserPort {

    User loadUserById(Long id);
}
