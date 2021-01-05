package io.robusta.team.service

import io.robusta.team.common.event.EventConsumer
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.lang.RuntimeException

@Component
@EventConsumer
class TeamEventHandler(
        private val teamService: TeamService
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleTeamSavedEvent(teamSavedEvent: TeamSavedEvent) {
        System.err.println("Event with id ${teamSavedEvent.source} is consumed")
        teamService.saveLog("A team saved, Team Id: ${teamSavedEvent.source}")
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleTeamSavedEvent2(teamSavedEvent: TeamSavedEvent) {
        System.err.println("Event with id ${teamSavedEvent.source} is consumed ----- 2")
        teamService.saveLog("A team saved, Team Id: ${teamSavedEvent.source} ----- 2")
        //throw RuntimeException("aaa")
    }
}
