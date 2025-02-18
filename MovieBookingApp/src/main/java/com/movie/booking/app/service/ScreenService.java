package com.movie.booking.app.service;

import com.movie.booking.app.models.Screen;
import com.movie.booking.app.repository.TheatreRepository;
import com.movie.booking.app.models.Theatre;
import com.movie.booking.app.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired private TheatreRepository theatreRepository;

    public Screen addScreenToTheatre(Long theatreId, Screen screen) throws Exception {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new Exception("Theatre not found for ID : "+theatreId));
        screen.setTheatre(theatre);
        return screenRepository.save(screen);
    }

    public List<Screen> findByTheatreId(Long theatreId) {
        return screenRepository.findByTheatreId(theatreId);
    }

    public Optional<Screen> findByScreenId(Long screenId) {
        return screenRepository.findById(screenId);
    }

    public List<Screen> getScreensByTheatre(Long theatreId) {
        return screenRepository.findByTheatreId(theatreId);
    }
}