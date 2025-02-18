package com.movie.booking.app.repository;

import com.movie.booking.app.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

}
