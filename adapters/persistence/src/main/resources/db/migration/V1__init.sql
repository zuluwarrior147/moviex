CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 25;

CREATE SEQUENCE movie_seq
    START WITH 1
    INCREMENT BY 25;

CREATE SEQUENCE rental_seq
    START WITH 1
    INCREMENT BY 25;

CREATE TABLE movie (
    id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    type INT,
    duration BIGINT,
    release_date DATE,
    created_at TIMESTAMP,
    last_modified TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    bonus_points INT,
    created_at TIMESTAMP,
    last_modified TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE rental (
    id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    initial_rental_days INT,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP,
    last_modified TIMESTAMP,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS users
    ADD CONSTRAINT uk_user
        UNIQUE (email);

ALTER TABLE IF EXISTS rental
    ADD CONSTRAINT fk_movie
        FOREIGN KEY (movie_id)
            REFERENCES movie;

ALTER TABLE IF EXISTS rental
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users;