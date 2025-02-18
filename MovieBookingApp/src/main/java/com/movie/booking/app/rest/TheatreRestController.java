package com.movie.booking.app.rest;

import com.movie.booking.app.models.Theatre;
import com.movie.booking.app.service.TheatreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
@Tag(name = "Theatre API", description = "Manage Theatres")
public class TheatreRestController {
    @Autowired
    private TheatreService theatreService;

    @Operation(summary = "Get all theatres")
    @GetMapping
    public List<Theatre> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    @Operation(summary = "Get theatre by ID")
    @GetMapping("/{id}")
    public Theatre getTheatre(@PathVariable Long id) throws Exception {
        return theatreService.getTheatreById(id);
    }

    @Operation(summary = "Add new theatre")
    @PostMapping
    public Theatre createTheatre(@RequestBody Theatre theatre) {
        return theatreService.saveTheatre(theatre);
    }

    @Operation(summary = "Update theatre by ID")
    @PutMapping("/{id}")
    public Theatre updateTheatre(@PathVariable Long id, @RequestBody Theatre theatre) throws Exception {
        return theatreService.updateTheatre(id, theatre);
    }

    @Operation(summary = "Delete theatre by ID")
    @DeleteMapping("/{id}")
    public void deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
    }
}

