package io.robusta.tournament.service;

import io.robusta.tournament.controller.payload.CreateTournamentPayload;
import io.robusta.tournament.controller.payload.TournamentPayload;
import io.robusta.tournament.entity.Tournament;
import io.robusta.tournament.repository.TournamentRepository;
import io.robusta.tournament.service.exception.DublicateTournamentNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository repository;

    public TournamentService(TournamentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Tournament create(CreateTournamentPayload payload) {
        Boolean isNameExisting = repository.existsByNameIgnoreCase(payload.getName());
        if (isNameExisting) {
            throw new DublicateTournamentNameException();
        }
        return repository.save(new Tournament(payload.getName()));
    }

    @Transactional(readOnly = true)
    public List<TournamentPayload> getAllTournaments() {
        return repository.findAll().stream().map((it) ->
                new TournamentPayload(it.getId(), it.getName())).collect(Collectors.toList()
        );
    }
}
