package com.rental.moviex.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
@Accessors(chain = true)
public class UserDetailsEntity {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int bonusPoints;
}
