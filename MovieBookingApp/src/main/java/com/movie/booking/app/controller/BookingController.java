package com.movie.booking.app.controller;


import com.movie.booking.app.models.Booking;
import com.movie.booking.app.service.BookingService;
import com.movie.booking.app.service.MovieService;
import com.razorpay.RazorpayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Controller
public class BookingController {

    private static final Logger logger = LogManager.getLogger(BookingController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking.html")
    public String booking(Model model) {
        List<String> banners = Arrays.asList(
                "/theme/img/banner1.jpg",
                "/theme/img/banner2.jpg",
                "/theme/img/banner3.jpg",
                "/theme/img/banner4.jpg"
        );
        model.addAttribute("pageTitle", "Movies");
        model.addAttribute("banners", banners);
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies/booking";
    }

    @PostMapping(value = "/createBooking", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking razorpayOrder = null;
        try {
            razorpayOrder = bookingService.createBooking(booking);
            logger.info("TNSPL Order : " + razorpayOrder.toString());
        } catch (RazorpayException e) {
            logger.info("Error in creating order : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(razorpayOrder, HttpStatus.CREATED);
    }

    @PostMapping(value = "/payment_status.html", produces = "application/json")
    public String paymentCallBack(@RequestParam Map<String, String> response) {
        Booking booking = bookingService.updateStatus(response);
        if (booking == null) {
            return "movies/failure";
        }
        return "movies/success";
    }
}
