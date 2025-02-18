package com.movie.booking.app.service;

import com.movie.booking.app.repository.TheatreRepository;
import com.movie.booking.app.models.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Theatre getTheatreById(Long id) throws Exception {
        return theatreRepository.findById(id)
                .orElseThrow(() -> new Exception("Theatre not found for ID : " + id));
    }

    public Theatre saveTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public Theatre updateTheatre(Long id, Theatre theatre) throws Exception {
        Theatre existing = theatreRepository.findById(id)
                .orElseThrow(() -> new Exception("Theatre not found for ID : " + id));
        if (theatre.getName() != null) {
            existing.setName(theatre.getName());
        }
        if (theatre.getLocation() != null) {
            existing.setLocation(theatre.getLocation());
        }
        if (theatre.getCity() != null) {
            existing.setCity(theatre.getCity());
        }
        return theatreRepository.save(existing);
    }

    public void deleteTheatre(Long id) {
        theatreRepository.deleteById(id);
    }
}
