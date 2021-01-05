package io.robusta.team.common.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.context.ApplicationEvent
import java.util.*

@JsonIgnoreProperties(value = ["timestamp", "eventName", "fallback"], ignoreUnknown = true)
open class Event(id: Long) : ApplicationEvent(id) {
    lateinit var eventId: UUID
    var fallback = false

    fun getEventName(): String {
        return javaClass.name
    }
}
