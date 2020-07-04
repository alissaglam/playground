package io.robusta.team.controller

import io.robusta.team.controller.payload.TeamPayload
import io.robusta.team.service.TeamService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TeamController(
        private val service: TeamService
) {

    @GetMapping("/teams")
    fun listTeams(): List<TeamPayload> {
        return service.getAllTeams().map { TeamPayload(it.name) }
    }
}
