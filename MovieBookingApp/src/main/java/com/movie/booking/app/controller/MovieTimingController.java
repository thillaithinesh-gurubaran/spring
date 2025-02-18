package com.movie.booking.app.controller;

import com.movie.booking.app.models.Movie;
import com.movie.booking.app.models.MovieTiming;
import com.movie.booking.app.models.Screen;
import com.movie.booking.app.repository.MovieRepository;
import com.movie.booking.app.repository.ScreenRepository;
import com.movie.booking.app.service.MovieTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/timings")
public class MovieTimingController {

    @Autowired
    private MovieTimingService movieTimingService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @GetMapping("/screen/{screenId}")
    public String showAddTimingForm(@PathVariable Long screenId, Model model) {
        MovieTiming movieTiming = new MovieTiming();
        model.addAttribute("pageTitle", "Add Movie Timings");
        model.addAttribute("movieTiming", movieTiming);
        model.addAttribute("movies", movieRepository.findMoviesByScreenId(screenId));
        model.addAttribute("screen", screenRepository.findById(screenId).orElse(null));
        return "movies/timings";
    }

    @PostMapping("/save")
    public String saveMovieTiming(@ModelAttribute MovieTiming movieTiming, @RequestParam Long theatreId,
                                  @RequestParam Long screenId, @RequestParam Long movieId) {
        Screen screen = screenRepository.findById(screenId).orElse(null);
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if (screen != null && movie != null) {
            movieTiming.setScreen(screen);
            movieTiming.setMovie(movie);
            movieTimingService.saveMovieTiming(movieTiming);
        }
        return "redirect:/theatres/" + theatreId;
    }
}
