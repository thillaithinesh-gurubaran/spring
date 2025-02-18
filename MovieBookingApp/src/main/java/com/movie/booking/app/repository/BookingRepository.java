package com.movie.booking.app.repository;

import com.movie.booking.app.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByRazorpayOrderId(String razorpayId);
}
