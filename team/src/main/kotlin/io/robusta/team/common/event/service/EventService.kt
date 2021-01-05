package io.robusta.team.common.event.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.robusta.team.common.event.Event
import io.robusta.team.common.event.entity.EventConsume
import io.robusta.team.common.event.entity.EventProduce
import io.robusta.team.common.event.entity.FallbackEventConsumeLog
import io.robusta.team.common.event.repository.EventConsumeRepository
import io.robusta.team.common.event.repository.EventProduceRepository
import io.robusta.team.common.event.repository.FallbackEventConsumeLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class EventService(
        private val eventConsumeRepository: EventConsumeRepository,
        private val eventProduceRepository: EventProduceRepository,
        private val fallbackEventConsumeLogRepository: FallbackEventConsumeLogRepository
) {

    fun saveEventProduce(event: Event) {
        val eventId = UUID.randomUUID()
        event.eventId = eventId
        val eventJsonPayload = jacksonObjectMapper().writeValueAsString(event)
        eventProduceRepository.save(EventProduce(eventId, event.getEventName(), eventJsonPayload, LocalDateTime.now()))
    }

    fun saveEventConsume(event: Event) {
        eventConsumeRepository.save(EventConsume(event.eventId, LocalDateTime.now(), event.fallback))
    }

    fun saveFallbackEventConsumerLog(lastEvent: EventProduce?, queryTime: LocalDateTime) {
        val fallbackEventConsumerLog = getFallbackEventConsumerLog()

        val eventId = lastEvent?.id ?: fallbackEventConsumerLog.lastlyConsumedEventId
        val lastEventTime = lastEvent?.createdAt ?: queryTime.minusMinutes(1)

        fallbackEventConsumeLogRepository.save(fallbackEventConsumerLog.copy(lastlyConsumedEventId = eventId, lastlyConsumedEventProduceTime = lastEventTime))
    }

    fun getFallbackEventConsumerLog(): FallbackEventConsumeLog {
        return fallbackEventConsumeLogRepository.findAll().firstOrNull()
                ?: FallbackEventConsumeLog(1, UUID.randomUUID(), LocalDateTime.now().minusYears(2))
    }
}
