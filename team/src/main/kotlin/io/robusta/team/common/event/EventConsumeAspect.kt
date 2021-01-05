package io.robusta.team.common.event

import io.robusta.team.common.event.service.EventService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component


@Component
@Aspect
class EventConsumeAspect(
        private val eventService: EventService
) {

    @Around("execution(* (@EventConsumer *).*(..))")
    fun eventConsumeLog(joinPoint: ProceedingJoinPoint): Any? {
        val event = joinPoint.args[0] as Event
        eventService.saveEventConsume(event)
        return joinPoint.proceed()
    }
}
