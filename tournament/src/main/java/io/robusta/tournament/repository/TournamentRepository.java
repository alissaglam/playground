package io.robusta.tournament.repository;

import io.robusta.tournament.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    public Boolean existsByNameIgnoreCase(String name);
}
