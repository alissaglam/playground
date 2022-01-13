package io.robusta.team.service

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import io.robusta.team.entity.Team
import io.robusta.team.repository.TeamRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamQueryResolver(
        private val teamRepository: TeamRepository
) : GraphQLQueryResolver {

    @Transactional(readOnly = true)
    fun teams() = teamRepository.findAll().toList()

    @Transactional(readOnly = true)
    fun getTeamsById(id: Long): Team = teamRepository.findByIdOrNull(id) ?: throw RuntimeException("Team not found")
}
