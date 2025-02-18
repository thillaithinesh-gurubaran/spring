package com.movie.booking.app.rest;

import com.movie.booking.app.models.Movie;
import com.movie.booking.app.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movie API", description = "Manage Movies")
public class MovieRestController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "Assign movie to a screen")
    @PostMapping("/{screenId}")
    public Movie addMovie(@PathVariable Long screenId, @RequestBody Movie movie) throws Exception {
        return movieService.addMovieToScreen(screenId, movie);
    }

    @Operation(summary = "Get all movies")
    @GetMapping
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return movies;
    }
}
