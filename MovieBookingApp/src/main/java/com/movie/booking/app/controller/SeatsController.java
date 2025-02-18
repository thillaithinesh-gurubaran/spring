package com.movie.booking.app.controller;

import com.movie.booking.app.models.Screen;
import com.movie.booking.app.models.Seat;
import com.movie.booking.app.service.ScreenService;
import com.movie.booking.app.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Controller
public class SeatsController {

    private static final Logger logger = LogManager.getLogger(SeatsController.class);

    @Autowired
    private ScreenService screenService;
    @Autowired
    private SeatService seatService;

    @GetMapping("/seats/{screenId}")
    public String getSeats(@PathVariable Long screenId, Model model) {
        logger.info("Get Seats for Screen ID : " + screenId);
        Optional<Screen> screen = screenService.findByScreenId(screenId);
        if (screen.isPresent()) {

            List<Seat> seats = screen.get().getSeats();

            // Grouping seats by category
            Map<String, List<List<Seat>>> categorizedSeats = categorizeSeats(seats);

            // Add to model
            model.addAttribute("categorizedSeats", categorizedSeats);
        } else {
            model.addAttribute("categorizedSeats", new HashMap<>());
        }
        model.addAttribute("pageTitle", "Select your seats");
        return "movies/seats"; // Thymeleaf template name
    }

    @GetMapping("/seats/screen/{screenId}/movie/{movieId}/timing/{timingId}")
    public String getSeats(@PathVariable Long screenId, @PathVariable Long movieId,
                           @PathVariable Long timingId, Model model) {
        logger.info("Screen Id : " + screenId + " Movie Id : " + screenId + " Timing Id : " + screenId);
        List<Seat> seats = seatService.findSeats(screenId, movieId, timingId);
        if (seats.isEmpty()) {
            model.addAttribute("categorizedSeats", new HashMap<>());
        } else {
            // Grouping seats by category
            Map<String, List<List<Seat>>> categorizedSeats = categorizeSeats(seats);

            // Add to model
            model.addAttribute("categorizedSeats", categorizedSeats);
        }
        model.addAttribute("pageTitle", "Select your seats");
        return "movies/seats"; // Thymeleaf template name
    }

    private Map<String, List<List<Seat>>> categorizeSeats(List<Seat> seats) {
        Map<String, List<List<Seat>>> categoryMap = new LinkedHashMap<>();

        for (Seat seat : seats) {
            categoryMap.putIfAbsent(seat.getCategory(), new ArrayList<>());
            while (categoryMap.get(seat.getCategory()).size() < seat.getRowNo()) {
                categoryMap.get(seat.getCategory()).add(new ArrayList<>());
            }
            categoryMap.get(seat.getCategory()).get(seat.getRowNo() - 1).add(seat);
        }
        return categoryMap;
    }

    @PostMapping("/seats/book")
    public String bookSeats(@RequestParam List<Long> selectedSeats) {
        logger.info("Selected Seats : " + selectedSeats);
        seatService.bookSeats(selectedSeats);
        return "movies/razorpay.html";
    }
}
