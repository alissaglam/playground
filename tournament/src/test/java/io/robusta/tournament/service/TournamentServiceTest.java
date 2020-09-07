package io.robusta.tournament.service;

import io.robusta.tournament.common.exception.ResourceNotFoundException;
import io.robusta.tournament.controller.payload.UpsertTournamentPayload;
import io.robusta.tournament.entity.Tournament;
import io.robusta.tournament.repository.TournamentRepository;
import io.robusta.tournament.service.exception.DuplicateTournamentNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTest {

    @Mock
    private TournamentRepository repository;

    @InjectMocks
    private TournamentService service;

    @Test
    public void testCreate() {
        given(repository.existsByNameIgnoreCase("test")).willReturn(false);
        service.create(new UpsertTournamentPayload("test"));
        ArgumentCaptor<Tournament> savedTournament = ArgumentCaptor.forClass(Tournament.class);
        verify(repository).save(savedTournament.capture());
        assertEquals("test", savedTournament.getValue().getName());
    }

    @Test
    public void testCreateNameAlreadyExisting() {
        given(repository.existsByNameIgnoreCase("test")).willReturn(true);
        assertThrows(DuplicateTournamentNameException.class, () -> {
            service.create(new UpsertTournamentPayload("test"));
        });
    }

    @Test
    public void testDelete() {
        given(repository.findById(1L)).willReturn(Optional.of(new Tournament("test")));
        service.delete(1L);
        ArgumentCaptor<Tournament> deletedTournament = ArgumentCaptor.forClass(Tournament.class);
        verify(repository).delete(deletedTournament.capture());
        assertEquals("test", deletedTournament.getValue().getName());
    }

    @Test
    public void testDeleteTournamentNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(1L);
        });
    }

    @Test
    public void testUpdate() {
        given(repository.findById(1L)).willReturn(Optional.of(new Tournament("test")));
        given(repository.findByNameIgnoreCase("test_updated")).willReturn(Optional.empty());
        service.update(1L, new UpsertTournamentPayload("test_updated"));
        ArgumentCaptor<Tournament> updatedTournament = ArgumentCaptor.forClass(Tournament.class);
        verify(repository).save(updatedTournament.capture());
        assertEquals("test_updated", updatedTournament.getValue().getName());
    }

    @Test
    public void testUpdateTournamentNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(1L, new UpsertTournamentPayload("test_updated"));
        });
    }

    @Test
    public void testUpdateTournamentSameNameAlreadyExisting() {
        given(repository.findById(1L)).willReturn(Optional.of(new Tournament("test")));
        Tournament existingTournament = new Tournament("test2");
        existingTournament.setId(2L);
        given(repository.findByNameIgnoreCase("test_updated")).willReturn(Optional.of(existingTournament));
        assertThrows(DuplicateTournamentNameException.class, () -> {
            service.update(1L, new UpsertTournamentPayload("test_updated"));
        });
    }
}
