package com.rental.moviex.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
@Getter
@Setter
@Accessors(chain = true)
@SequenceGenerator(name = "default_generator", sequenceName = "rental_seq", allocationSize = 25)
public class RentalEntity extends BaseEntity {
    @ManyToOne(optional = false)
    private UserEntity user;
    @ManyToOne(optional = false)
    private MovieEntity movie;
    private Integer initialRentalDays;
    private LocalDate startDate;
    private LocalDate endDate;
}
