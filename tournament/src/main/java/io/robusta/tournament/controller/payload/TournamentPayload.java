package io.robusta.tournament.controller.payload;

import io.robusta.tournament.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TournamentPayload {
    private Long id;
    private String name;

    public TournamentPayload(Tournament tournament) {
        this.id = tournament.getId();
        this.name = tournament.getName();
    }
}
