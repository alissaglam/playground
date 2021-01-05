package io.robusta.team.common.event

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Transactional(propagation = Propagation.REQUIRES_NEW)
annotation class EventConsumer
