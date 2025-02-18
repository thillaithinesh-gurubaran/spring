package com.movie.booking.app.service;

import com.movie.booking.app.repository.SeatRepository;
import com.movie.booking.app.models.Movie;
import com.movie.booking.app.models.MovieTiming;
import com.movie.booking.app.models.Screen;
import com.movie.booking.app.models.Seat;
import com.movie.booking.app.repository.MovieRepository;
import com.movie.booking.app.repository.MovieTimingRepository;
import com.movie.booking.app.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieTimingRepository movieTimingRepository;

    public List<Seat> addSeatsToScreen(Long screenId,
                                       Long movieId,
                                       Long timeId,
                                       int rows,
                                       int columns,
                                       String category
    ) throws Exception {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new Exception("Screen not found for ID : " + screenId));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new Exception("Movie not found for ID : " + movieId));

        MovieTiming movieTiming = movieTimingRepository.findById(timeId)
                .orElseThrow(() -> new Exception("Movie timings not found for ID : " + timeId));


        List<Seat> seats = new ArrayList<>();

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= columns; c++) {
                Seat seat = new Seat();
                seat.setRowNo(r);
                seat.setColumnNo(c);
                seat.setAvailable(true);
                seat.setCategory(category);
                // Set Objects
                seat.setScreen(screen);
                seat.setMovie(movie);
                seat.setMovieTiming(movieTiming);
                seats.add(seat);
            }
        }
        return seatRepository.saveAll(seats);
    }

    public Screen findByScreenId(Long screenId) {
        Optional<Seat> screen = seatRepository.findById(screenId);
        if (screen.isPresent()) {
            return screen.get().getScreen();
        }
        return null;
    }

    public void bookSeats(List<Long> seatIds) {
        seatRepository.bookSeats(seatIds);
    }

    public List<Seat> getSeatsByScreenId(Long screenId) {
        return seatRepository.findByScreenId(screenId);
    }

    public List<Seat> findSeats(Long movieId, Long screenId, Long movieTimingId) {
        // First, find the MovieTiming using the movieId, screenId, and movieTimingId
        MovieTiming movieTiming = movieTimingRepository.findById(movieTimingId)
                .orElseThrow(() -> new RuntimeException("Movie timing not found"));

        // Then find all seats related to this specific movieTiming
        return seatRepository.findByMovieTiming(movieTiming);
    }
}

