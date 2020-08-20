package io.robusta.tournament.controller;

import io.robusta.tournament.controller.payload.CreateTournamentPayload;
import io.robusta.tournament.controller.payload.TournamentPayload;
import io.robusta.tournament.entity.Tournament;
import io.robusta.tournament.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TournamentController {
    private final TournamentService service;

    public TournamentController(TournamentService service) {
        this.service = service;
    }

    @GetMapping("/tournaments")
    public List<TournamentPayload> list() {
        return service.getAllTournaments();
    }

    @PostMapping("/tournaments")
    public ResponseEntity<Object> create(@RequestBody CreateTournamentPayload payload) {
        Tournament tournament = service.create(payload);
        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tournament.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
