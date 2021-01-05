package io.robusta.team.common.event

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.robusta.team.common.event.repository.EventProduceRepository
import io.robusta.team.common.event.repository.FallbackEventConsumeLogRepository
import io.robusta.team.common.event.service.EventService
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class FallbackEventConsumer(
        private val eventProduceRepository: EventProduceRepository,
        private val fallbackEventConsumeLogRepository: FallbackEventConsumeLogRepository,
        private val applicationEventPublisher: ApplicationEventPublisher,
        private val eventService: EventService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 * * * * *  ")
    @SchedulerLock(name = "TaskScheduler_fallbackEventConsume",
            lockAtLeastFor = "PT5M", lockAtMostFor = "PT14M")
    @Transactional
    fun fallbackEventConsume() {
        logger.info("Fallback event consumer...")

        val fallbackEventConsumeLog = eventService.getFallbackEventConsumerLog()
        val queryTime = LocalDateTime.now()
        val unprocessedEvents = eventProduceRepository.getUnprocessedEvents(fallbackEventConsumeLog.lastlyConsumedEventProduceTime).toList()
        unprocessedEvents.forEach { event ->
            val applicationEvent = jacksonObjectMapper().readValue(event.payload, Class.forName(event.event))
            (applicationEvent as Event).fallback = true
            applicationEventPublisher.publishEvent(applicationEvent)
        }

        eventService.saveFallbackEventConsumerLog(unprocessedEvents.lastOrNull(), queryTime)
    }
}
