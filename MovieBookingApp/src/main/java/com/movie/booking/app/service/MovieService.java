package com.movie.booking.app.service;

import com.movie.booking.app.models.Movie;
import com.movie.booking.app.models.Screen;
import com.movie.booking.app.repository.MovieRepository;
import com.movie.booking.app.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreenRepository screenRepository;

    public Movie addMovieToScreen(Long screenId, Movie movie) throws Exception {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new Exception("Screen not found for ID : "+screenId));
        movie.setScreen(screen);
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByScreen(Long screenId) {
        return movieRepository.findMoviesByScreenId(screenId);
    }
}
