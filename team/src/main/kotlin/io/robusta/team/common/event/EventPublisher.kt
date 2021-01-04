package io.robusta.team.common.event

import io.robusta.team.common.event.service.EventService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EventPublisher(
        private val applicationEventPublisher: ApplicationEventPublisher,
        private val eventService: EventService
) {

    @Transactional
    fun publishEvent(event: Event) {
        eventService.saveEventProduce(event)
        applicationEventPublisher.publishEvent(event)
    }
}
