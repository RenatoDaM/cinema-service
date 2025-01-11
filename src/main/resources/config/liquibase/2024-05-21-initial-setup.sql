--liquibase formatted sql
--changeset thiagoindalecio:initial-database-setup logicalFilePath:initial-setup
CREATE TABLE movie (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    synopsis TEXT,
    age_group INT NOT NULL,
    category VARCHAR(255) NOT NULL,
    release_date DATE NOT NULL,
    duration TIME NOT NULL,
    director VARCHAR(255) NOT NULL,
    movie_cast VARCHAR(255),
    producer VARCHAR(255) NOT NULL
);

CREATE TABLE room (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    room_number INT NOT NULL,
    total_seats SMALLINT NOT NULL
);

CREATE TABLE session (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    session_start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    movie_id BIGINT,
    room_id BIGINT,
    base_price NUMERIC(10, 2),
    CONSTRAINT fk_movie_id FOREIGN KEY (movie_id) REFERENCES movie (id),
    CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES room (id)
);

CREATE TABLE ticket (
                    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    uuid VARCHAR(36) NOT NULL,
                    paid_price NUMERIC(10,2) NOT NULL,
                    discount_type VARCHAR(30) NOT NULL
);

CREATE TABLE seat (
                      id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                      seat_number SMALLINT NOT NULL,
                      available BOOLEAN NOT NULL,
                      type VARCHAR(255) NOT NULL,
                      session_id BIGINT,
                      ticket_id BIGINT,
                      CONSTRAINT fk_session_id FOREIGN KEY (session_id) REFERENCES session (id),
                      CONSTRAINT fk_ticket_id FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);