package io.robusta.team.controller

import io.robusta.team.controller.payload.TeamPayload
import io.robusta.team.controller.payload.UpsertTeamPayload
import io.robusta.team.entity.Team
import io.robusta.team.service.TeamService
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
class TeamController(
        private val service: TeamService
) {

    @GetMapping("/teams")
    fun listTeams(): List<TeamPayload> {
        return service.getAllTeams().map { TeamPayload(it.id!!, it.name) }
    }

    @PostMapping("/teams")
    fun saveTeam(@RequestBody teamPayload: UpsertTeamPayload): TeamPayload {
        val team: Team = service.save(teamPayload)
        return TeamPayload(team.id!!, team.name)
    }

    @DeleteMapping("/teams/{teamId}")
    fun deleteTeam(@PathVariable teamId: Long) {
        service.delete(teamId)
    }
}
