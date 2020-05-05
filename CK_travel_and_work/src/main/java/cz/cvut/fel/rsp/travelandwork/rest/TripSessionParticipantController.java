package cz.cvut.fel.rsp.travelandwork.rest;

import cz.cvut.fel.rsp.travelandwork.dto.RequestWrapperTripSessionsParticipants;
import cz.cvut.fel.rsp.travelandwork.dto.TripSessionDto;
import cz.cvut.fel.rsp.travelandwork.exception.NotFoundException;
import cz.cvut.fel.rsp.travelandwork.model.TripSession;
import cz.cvut.fel.rsp.travelandwork.service.TripSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/trip/participants")
public class TripSessionParticipantController {

    private TripSessionService tripSessionService;

    @Autowired
    public TripSessionParticipantController(TripSessionService tripSessionService) {
        this.tripSessionService = tripSessionService;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{trip_short_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestWrapperTripSessionsParticipants> findAllParticipants(@PathVariable String trip_short_name) {
        return tripSessionService.findAllParticipants(trip_short_name);
    }
}
