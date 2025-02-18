package com.movie.booking.app.rest;

import com.movie.booking.app.models.Seat;
import com.movie.booking.app.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@Tag(name = "Seat API", description = "Manage Seats")
public class SeatRestController {

    private static final Logger logger = LogManager.getLogger(SeatRestController.class);
    
    @Autowired
    private SeatService seatService;

    @Operation(summary = "Add seats by category to an screen for movie with show timings")
    @PostMapping("/screen/{screenId}/movie/{movieId}/timing/{timingId}")
    public List<Seat> addSeats(@PathVariable Long screenId,
                               @PathVariable Long movieId,
                               @PathVariable Long timingId,
                               @RequestParam int rows,
                               @RequestParam int columns,
                               @RequestParam String category
    ) throws Exception {
        return seatService.addSeatsToScreen(screenId, movieId, timingId, rows, columns, category);
    }

    @Operation(summary = "Get seats by screen id")
    @GetMapping("/{screenId}")
    public List<Seat> getSeatsByScreen(@PathVariable Long screenId) {
        logger.info("Screen Id : " + screenId);
        return seatService.getSeatsByScreenId(screenId);
    }

    @Operation(summary = "Get seats by screen, movie and timings id")
    @GetMapping("/screen/{screenId}/movie/{movieId}/timing/{timingId}")
    public List<Seat> getSeats(@PathVariable Long screenId, @PathVariable Long movieId, @PathVariable Long timingId) {
        logger.info("Screen Id : " + screenId + " Movie Id : " + screenId + " Timing Id : " + screenId);
        return seatService.findSeats(screenId, movieId, timingId);
    }
}
