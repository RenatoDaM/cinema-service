package com.cinema.dataproviders.database.repository;

import com.cinema.core.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
