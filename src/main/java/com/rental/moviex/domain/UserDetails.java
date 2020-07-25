package com.rental.moviex.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDetails {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int bonusPoints;

    void increaseBonusPoints(int points) {
        bonusPoints += points;
    }
}
