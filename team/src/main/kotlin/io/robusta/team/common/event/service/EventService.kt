package io.robusta.team.common.event.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.robusta.team.common.event.Event
import io.robusta.team.common.event.entity.EventConsume
import io.robusta.team.common.event.entity.EventProduce
import io.robusta.team.common.event.repository.EventConsumeRepository
import io.robusta.team.common.event.repository.EventProduceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class EventService(
        private val eventConsumeRepository: EventConsumeRepository,
        private val eventProduceRepository: EventProduceRepository
) {

    @Transactional
    fun saveEventProduce(event: Event) {
        val eventId = UUID.randomUUID()
        event.eventId = eventId
        val eventJsonPayload = jacksonObjectMapper().writeValueAsString(event)
        eventProduceRepository.save(EventProduce(eventId, event.getEventName(), eventJsonPayload, Instant.now()))
    }

    @Transactional
    fun saveEventConsume(event: Event) {
        eventConsumeRepository.save(EventConsume(event.eventId, Instant.now()))
    }
}
