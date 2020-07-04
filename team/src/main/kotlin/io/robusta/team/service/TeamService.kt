package io.robusta.team.service

import io.robusta.team.entity.Team
import io.robusta.team.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService(
        private val teamRepository: TeamRepository
) {

    @Transactional(readOnly = true)
    fun getAllTeams(): List<Team> {
        return teamRepository.findAll().toList()
    }
}
