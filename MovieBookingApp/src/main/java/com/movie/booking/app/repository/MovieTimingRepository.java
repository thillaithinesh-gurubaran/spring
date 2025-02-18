package com.movie.booking.app.repository;

import com.movie.booking.app.models.MovieTiming;
import com.movie.booking.app.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieTimingRepository extends JpaRepository<MovieTiming, Long> {
    List<MovieTiming> findByScreen(Screen screen);
}
