package io.robusta.team.service

import io.robusta.team.common.event.Event

class TeamSavedEvent(teamId: Long): Event(teamId)
