package io.robusta.tournament.controller.payload;

import io.robusta.tournament.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TournamentTeamsPayload {
    private Long tournamentId;
    private String tournamentName;
    private List<TeamPayload> teams;

    public TournamentTeamsPayload(Tournament tournament, List<TeamPayload> teams) {
        this.tournamentId = tournament.getId();
        this.tournamentName = tournament.getName();
        this.teams = teams;
    }
}
