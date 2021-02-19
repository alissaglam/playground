package io.robusta.tournament.service;

import io.robusta.tournament.common.exception.ResourceNotFoundException;
import io.robusta.tournament.controller.payload.TeamPayload;
import io.robusta.tournament.controller.payload.TournamentTeamsPayload;
import io.robusta.tournament.controller.payload.UpsertTournamentPayload;
import io.robusta.tournament.entity.Tournament;
import io.robusta.tournament.repository.TournamentRepository;
import io.robusta.tournament.service.exception.DuplicateTournamentNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository repository;

    @Value("${teams-service-url}")
    private String teamsServiceUrl;

    @Autowired
    public TournamentService(TournamentRepository repository) {
        this.repository = repository;
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

    @Transactional(readOnly = true)
    public TournamentTeamsPayload getTeamsByTournamentId(Long tournamentId, Long teamServiceWaitInMilis) {
        Tournament tournament = repository.findById(tournamentId).orElseThrow(() -> new ResourceNotFoundException(Tournament.class, tournamentId));
        String teamsUrl = teamsServiceUrl + "/teams";
        teamsUrl = teamServiceWaitInMilis != null ? teamsUrl + "?waitInMilis=" + teamServiceWaitInMilis : teamsUrl;

        RestTemplate restTemplate = createRestTemplate();
        ResponseEntity<List<TeamPayload>> response = restTemplate.exchange(
                teamsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<TeamPayload>>() {});
        List<TeamPayload> teams = response.getBody();
        return new TournamentTeamsPayload(tournament, teams);
    }

    private RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectionRequestTimeout(10000);
        return new RestTemplate(requestFactory);
    }
}
