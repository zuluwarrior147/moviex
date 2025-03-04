package com.rental.moviex.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
public class UserDetails {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int bonusPoints;

    void increaseBonusPoints(int points) {
        bonusPoints += points;
    }
}
