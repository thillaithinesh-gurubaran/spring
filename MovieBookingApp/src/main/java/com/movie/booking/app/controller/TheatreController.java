package com.movie.booking.app.controller;

import com.movie.booking.app.models.Screen;
import com.movie.booking.app.models.Theatre;
import com.movie.booking.app.service.ScreenService;
import com.movie.booking.app.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private ScreenService screenService;

    @GetMapping("/")
    public String firstPage(Model model) {
        // Get theatre
        List<Theatre> theatres = theatreService.getAllTheatres();
        model.addAttribute("pageTitle", "Theatres");
        model.addAttribute("theatres", theatres);
        return "movies/theatres";
    }

    @GetMapping("/theatre.html")
    public String theatre() {
        return "movies/theatre";
    }

    @GetMapping("/theatres.html")
    public String theatres(Model model) throws Exception {
        // Get theatre
        List<Theatre> theatres = theatreService.getAllTheatres();
        model.addAttribute("pageTitle", "Theatres");
        model.addAttribute("theatres", theatres);
        return "movies/theatres";
    }

    @GetMapping("/theatres/{theatreId}")
    public String viewTheatreDetails(@PathVariable Long theatreId, Model model) throws Exception {
        Theatre theatre = theatreService.getTheatreById(theatreId);
        model.addAttribute("pageTitle", "Theatre Screens");
        model.addAttribute("theatre", theatre);
        return "movies/theatre-details";
    }

    @GetMapping("/screens/view/{screenId}")
    public String viewScreenDetails(@PathVariable Long screenId, Model model) {
        Screen screen = screenService.findByScreenId(screenId).orElse(null);
        model.addAttribute("screen", screen);
        return "movies/timings_view";
    }
}
