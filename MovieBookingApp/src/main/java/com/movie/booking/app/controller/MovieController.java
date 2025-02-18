package com.movie.booking.app.controller;

import com.movie.booking.app.models.Movie;
import com.movie.booking.app.models.Screen;
import com.movie.booking.app.models.Theatre;
import com.movie.booking.app.service.MovieService;
import com.movie.booking.app.service.ScreenService;
import com.movie.booking.app.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/theatre/{theatreId}/movies")
    public String getMoviesByTheatre(@PathVariable Long theatreId, Model model) throws Exception {
        Theatre theatre = theatreService.getTheatreById(theatreId);
        List<Screen> screens = screenService.getScreensByTheatre(theatreId);

        Map<Screen, List<Movie>> screenMoviesMap = new LinkedHashMap<>();
        for (Screen screen : screens) {
            List<Movie> movies = movieService.getMoviesByScreen(screen.getId());
            screenMoviesMap.put(screen, movies);
        }

        model.addAttribute("theatre", theatre);
        model.addAttribute("screenMoviesMap", screenMoviesMap);

        return "movies/list_movies";
    }


    @GetMapping("/movies.html")
    public String getBanners(Model model) {
        List<String> banners = Arrays.asList("/theme/img/banner1.jpg", "/theme/img/banner2.jpg", "/theme/img/banner3.jpg", "/theme/img/banner4.jpg");
        model.addAttribute("pageTitle", "Book Your Tickets");
        model.addAttribute("banners", banners);
        return "movies/movies"; // Thymeleaf template name
    }
}
