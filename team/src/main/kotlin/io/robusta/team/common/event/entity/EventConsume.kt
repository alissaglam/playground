package io.robusta.team.common.event.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class EventConsume(
        @Id
        val id: UUID,
        val createdAt: LocalDateTime,
        val fallback: Boolean
)
