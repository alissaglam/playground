package io.robusta.team.service

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import io.robusta.team.entity.Team
import io.robusta.team.repository.TeamRepository
import org.springframework.stereotype.Service

@Service
class TeamMutationResolver(
        private val teamRepository: TeamRepository
) : GraphQLMutationResolver {

    fun createTeam(name: String): Team {
        val team = Team(name = name)
        return teamRepository.save(team)
    }
}
