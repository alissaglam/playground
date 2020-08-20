package io.robusta.tournament.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TournamentPayload {
    private Long id;
    private String name;
}
