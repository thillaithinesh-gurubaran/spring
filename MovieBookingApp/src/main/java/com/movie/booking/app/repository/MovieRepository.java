package com.movie.booking.app.repository;

import com.movie.booking.app.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findMoviesByScreenId(Long screenId);
    Movie findMovieByScreenId(Long screenId);
}
