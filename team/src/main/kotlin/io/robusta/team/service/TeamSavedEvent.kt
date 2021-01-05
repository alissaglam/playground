package io.robusta.team.service

import io.robusta.team.common.event.Event

data class TeamSavedEvent(val teamId: Long): Event(teamId)
