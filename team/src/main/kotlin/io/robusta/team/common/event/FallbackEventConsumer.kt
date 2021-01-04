package io.robusta.team.common.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.robusta.team.common.event.entity.FallbackEventConsumeLog
import io.robusta.team.common.event.repository.EventProduceRepository
import io.robusta.team.common.event.repository.FallbackEventConsumeLogRepository
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Component
class FallbackEventConsumer(
        private val eventProduceRepository: EventProduceRepository,
        private val fallbackEventConsumeLogRepository: FallbackEventConsumeLogRepository,
        private val applicationEventPublisher: ApplicationEventPublisher
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 * * * * *  ")
    @SchedulerLock(name = "TaskScheduler_fallbackEventConsume",
            lockAtLeastFor = "PT5M", lockAtMostFor = "PT14M")
    @Transactional
    fun fallbackEventConsume() {
        logger.info("Fallback event consumer...")
        val fallbackEventConsumeLog = fallbackEventConsumeLogRepository.findAll().firstOrNull()
                ?: FallbackEventConsumeLog(1, UUID.randomUUID(), LocalDateTime.now().minusYears(2))
        val unprocessedEvents = eventProduceRepository.getUnprocessedEventsTop1000(fallbackEventConsumeLog.lastlyConsumedEventProduceTime).toList()
        unprocessedEvents.forEach { event ->
            val applicationEvent = jacksonObjectMapper().readValue(event.payload, Class.forName(event.event))
            applicationEventPublisher.publishEvent(applicationEvent)
        }

        val updatedFallbackConsumeLog = unprocessedEvents.lastOrNull()?.let { lastEvent ->
            fallbackEventConsumeLog.copy(lastlyConsumedEventId = lastEvent.id, lastlyConsumedEventProduceTime = lastEvent.createdAt)
        } ?: fallbackEventConsumeLog
        fallbackEventConsumeLogRepository.save(updatedFallbackConsumeLog)
    }
}
