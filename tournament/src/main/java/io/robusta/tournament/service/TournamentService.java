package io.robusta.tournament.service;

import io.robusta.tournament.common.exception.ResourceNotFoundException;
import io.robusta.tournament.controller.payload.AddTeamPayload;
import io.robusta.tournament.controller.payload.UpsertTournamentPayload;
import io.robusta.tournament.entity.Team;
import io.robusta.tournament.entity.Tournament;
import io.robusta.tournament.repository.TeamRepository;
import io.robusta.tournament.repository.TournamentRepository;
import io.robusta.tournament.service.exception.DuplicateTournamentNameException;
import io.robusta.tournament.service.exception.TeamAlreadyExistingInTournamentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TournamentService {

    private final TournamentRepository repository;
    private final TeamRepository teamRepository;

    @Autowired
    public TournamentService(TournamentRepository repository,
                             TeamRepository teamRepository) {
        this.repository = repository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Tournament create(UpsertTournamentPayload payload) {
        Boolean isNameExisting = repository.existsByNameIgnoreCase(payload.getName());
        if (isNameExisting) {
            throw new DuplicateTournamentNameException(payload.getName());
        }
        return repository.save(new Tournament(payload.getName()));
    }

    @Transactional
    public void delete(Long tournamentId) {
        Tournament tournament = getTournament(tournamentId);
        repository.delete(tournament);
    }

    @Transactional
    public Tournament update(Long tournamentId, UpsertTournamentPayload upsertTournamentPayload) {
        Tournament tournament = getTournament(tournamentId);
        checkTournamentNameNotExisting(upsertTournamentPayload, tournament);
        tournament.setName(upsertTournamentPayload.getName());
        return repository.save(tournament);
    }

    private void checkTournamentNameNotExisting(UpsertTournamentPayload upsertTournamentPayload, Tournament tournament) {
        repository.findByNameIgnoreCase(upsertTournamentPayload.getName()).ifPresent(it -> {
            if (!it.getId().equals(tournament.getId())) {
                throw new DuplicateTournamentNameException(upsertTournamentPayload.getName());
            }
        });
    }

    private Tournament getTournament(Long tournamentId) {
        return repository.findById(tournamentId).orElseThrow(() ->
                new ResourceNotFoundException(Tournament.class, tournamentId));
    }

    @Transactional
    public Tournament addTeam(Long tournamentId, AddTeamPayload addTeamPayload) {
        Tournament tournament = getTournament(tournamentId);
        checkTeamIsNotExistingInTheTournament(addTeamPayload, tournament);

        Team team = teamRepository.findById(addTeamPayload.getTeamId()).orElseThrow(() -> new ResourceNotFoundException(Team.class, addTeamPayload.getTeamId()));
        tournament.getTeams().add(team);
        return tournament;
    }

    private void checkTeamIsNotExistingInTheTournament(AddTeamPayload addTeamPayload, Tournament tournament) {
        Optional<Team> teamWithSameIdInTheTournament = tournament.getTeams().stream().filter(team -> team.getId().equals(addTeamPayload.getTeamId())).findFirst();
        if (teamWithSameIdInTheTournament.isPresent()) {
            throw new TeamAlreadyExistingInTournamentException(teamWithSameIdInTheTournament.get().getName(), tournament.getName());
        }
    }
}
