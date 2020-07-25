package com.rental.moviex.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
@SequenceGenerator(name = "default_generator", sequenceName = "user_seq", allocationSize = 25)
public class UserEntity extends BaseEntity{
    @Column(unique = true)
    private String email;
    private UserDetailsEntity details;
}
