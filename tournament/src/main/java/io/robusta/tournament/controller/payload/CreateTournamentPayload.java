package io.robusta.tournament.controller.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateTournamentPayload {
    private String name;
}
