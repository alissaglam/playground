package io.robusta.team.common.event.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class EventConsume(
        @Id
        val id: UUID,
        val createdAt: Instant
)
