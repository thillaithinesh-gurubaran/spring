package com.movie.booking.app.repository;

import com.movie.booking.app.models.MovieTiming;
import com.movie.booking.app.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.isAvailable = false WHERE s.id IN :seatIds")
    void bookSeats(@Param("seatIds") List<Long> seatIds);

    List<Seat> findByScreenId(Long screenId);

    List<Seat> findByMovieTiming(MovieTiming movieTiming);
}
