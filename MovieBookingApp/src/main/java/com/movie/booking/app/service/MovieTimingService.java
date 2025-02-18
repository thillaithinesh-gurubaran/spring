package com.movie.booking.app.service;

import com.movie.booking.app.repository.MovieTimingRepository;
import com.movie.booking.app.models.MovieTiming;
import com.movie.booking.app.models.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieTimingService {
    @Autowired
    private MovieTimingRepository movieTimingRepository;

    public List<MovieTiming> getMovieTimingsByScreen(Screen screen) {
        return movieTimingRepository.findByScreen(screen);
    }

    public Optional<MovieTiming> getMovieTimingById(Long movieTimingId) {
        return movieTimingRepository.findById(movieTimingId);
    }
    public void saveMovieTiming(MovieTiming movieTiming) {
        movieTimingRepository.save(movieTiming);
    }
}

