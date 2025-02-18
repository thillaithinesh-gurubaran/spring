package com.movie.booking.app.rest;

import com.movie.booking.app.models.Screen;
import com.movie.booking.app.service.ScreenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@Tag(name = "Screen API", description = "Manage Screens")
public class ScreenRestController {
    @Autowired
    private ScreenService screenService;

    @Operation(summary = "Add screen to a theatre")
    @PostMapping("/{theatreId}")
    public Screen addScreen(@PathVariable Long theatreId, @RequestBody Screen screen) throws Exception {
        return screenService.addScreenToTheatre(theatreId, screen);
    }

    @Operation(summary = "Get screens by theatre id")
    @GetMapping("/{theatreId}")
    public List<Screen> getScreens(@PathVariable Long theatreId) throws Exception {
        return screenService.findByTheatreId(theatreId);
    }
}