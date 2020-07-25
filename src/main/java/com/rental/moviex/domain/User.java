package com.rental.moviex.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {
    private Long id;
    private String email;
    private UserDetails details;

    public void addBonusPoints(int bonusPoints) {
        details.increaseBonusPoints(bonusPoints);
    }
}
