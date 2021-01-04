package io.robusta.team.service

import io.robusta.team.common.event.EventPublisher
import io.robusta.team.controller.payload.UpsertTeamPayload
import io.robusta.team.entity.Team
import io.robusta.team.entity.TeamLog
import io.robusta.team.repository.TeamLogRepository
import io.robusta.team.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService(
        private val teamRepository: TeamRepository,
        private val teamLogRepository: TeamLogRepository,
        private val eventPublisher: EventPublisher
) {

    @Transactional(readOnly = true)
    fun getAllTeams(): List<Team> {
        return teamRepository.findAll().toList()
    }

    @Transactional
    fun save(teamPayload: UpsertTeamPayload): Team {
        val team = Team(name = teamPayload.name)
        val savedTeam = teamRepository.save(team)
        eventPublisher.publishEvent(TeamSavedEvent(savedTeam.id!!))
        return savedTeam
    }

    @Transactional
    fun saveLog(log: String): TeamLog {
        return teamLogRepository.save(TeamLog(log = log))
    }

    @Transactional
    fun delete(teamId: Long) {
        val team: Team = teamRepository.findById(teamId).orElseThrow { RuntimeException("Team not found") }
        teamRepository.delete(team)
    }
}
